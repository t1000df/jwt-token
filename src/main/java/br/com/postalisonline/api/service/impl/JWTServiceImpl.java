package br.com.postalisonline.api.service.impl;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.postalisonline.api.bean.RequestToken;
import br.com.postalisonline.api.bean.ResponseToken;

import br.com.postalisonline.api.security.JWTKeys;
import br.com.postalisonline.api.security.JWTRS512Builder;
import br.com.postalisonline.api.security.JWTValidateException;

import br.com.postalisonline.api.service.JWTException;
import br.com.postalisonline.api.service.JWTService;
import io.jsonwebtoken.Claims;

/**
 * 
 * @author alessandro.lima@4quality
 *
 */
@Service
public class JWTServiceImpl implements JWTService {

		
	private static String POSTALIS_ISS = "postalis-identitymanager";
	
	@Autowired
	JWTRS512Builder jwtBuilder;
	
	@Autowired
	JWTKeys jwtKeys;
	
	@PostConstruct
	private void generateKeys() {
		
		 try {
			 
			 if (jwtKeys.getPrivateKey() == null) {
				 Map<String, Object> mapKeys = jwtBuilder.getRSAKeys();
				 jwtKeys.setPrivateKey((PrivateKey) mapKeys.get("private"));
				 jwtKeys.setPublicKey((PublicKey) mapKeys.get("public"));
			 }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
	
	@Override
	public ResponseToken generate(RequestToken requestToken) {
		
		ResponseToken tokenBean = new ResponseToken();
		
		Map<String,Object> claims = new HashMap<String,Object>();
		
		//tratamento do escopo
		String[] scope = (requestToken.getScope()==null  ) ? new String[]{"default"} : requestToken.getScope();
		
		if (scope.length == 0) {
			scope = new String[]{"default"};
		}
		
		//adicionando o escopo do token
		claims.put("scope", scope);
		
		if (requestToken.getClaims() !=null && !requestToken.getClaims().isEmpty()) {
			//adicionando os claims solicitados para o token
			for (String key: requestToken.getClaims().keySet()) {
				claims.put(key, requestToken.getClaims().get(key));
			}
		}
		
		//gerando o accesstoken
		String token = generateAccessToken(requestToken.getUser(), requestToken.getEmail(),claims);
		tokenBean.setAccessToken(token);
		
		//gerando o refreshtoken
		Map<String,Object> claimsR = new HashMap<String,Object>();
		claimsR.put("type", "refresh-token");
		String refreshToken = generateRefreshToken(requestToken.getUser(), requestToken.getEmail(), claims, claimsR);
		tokenBean.setRefreshToken(refreshToken);
		
		return tokenBean;
		
	}
	
	private String generateAccessToken(String id, String subject, Map<String,Object> claims) {
		
		claims.put("type", "access-token");
		String token = jwtBuilder.generateToken(jwtKeys.getPrivateKey(), id, subject, POSTALIS_ISS, jwtBuilder.getTokenTimeExpiration(),claims);
		
		return token;
		
	}
	
	private String generateRefreshToken(String id, String subject, Map<String,Object> claimsA,  Map<String,Object> claimsR) {
		
		String id64 = Base64.getEncoder().encodeToString(id.getBytes());
		String subject64 = Base64.getEncoder().encodeToString(subject.getBytes());
		
		//adicionando os claims do accessToken no refreshToken.
		//Essa operação é necessária para gerar um novo accessToken apartir de uma solicitação de refresh.
		for (String key: claimsA.keySet()) {
			if (key.equals("jti") || key.equals("type")) {
				continue;
			}
			claimsR.put("accessToken-"+key, claimsA.get(key));
		}
		
		claimsR.put("accessToken-jti", id);
		claimsR.put("accessToken-sub", subject);
		claimsR.put("type", "refresh-token");
		
		//String token = jwtbuilder.createJWT(id64, "postalis-api", subject64,jwtbuilder.getRefreshTokenTimeExpiration(),claimsR);
		String token = jwtBuilder.generateToken(jwtKeys.getPrivateKey(), id64, subject64, POSTALIS_ISS, jwtBuilder.getRefreshTokenTimeExpiration(),claimsR);
		
		return token;
	}

	@Override
	public String generateFake() {
		
		Map<String,Object> claims = new HashMap<String,Object>();
		claims.put("type", "access-token");
		claims.put("scope", "default");
		//String token = jwtbuilder.createJWT("66664330172", "postalis-api", "alessandro.lima@4quality.com.br", jwtbuilder.getTokenTimeExpiration(),claims);
		String token = jwtBuilder.generateToken(jwtKeys.getPrivateKey(), 
				"66664330172", 
				"alessandro.lima@4quality.com.br", 
				POSTALIS_ISS, 
				jwtBuilder.getTokenTimeExpiration());
		
		return token;
	}

	@Override
	public void validate(String token) throws JWTException {
		
		Claims claims = jwtBuilder.verifyToken(token, jwtKeys.getPublicKey());
		
		if (claims ==null ) {
			throw new JWTException("Token invalido ou expirado!");
		}
		
	}

	@Override
	public ResponseToken refresh(String refreshToken) throws JWTException {
		
		
		try {
			
			Map<String,Object> claimsR =  jwtBuilder.verifyToken(refreshToken, jwtKeys.getPublicKey());
			
			if (claimsR == null) {
				throw new JWTValidateException("Token inválido!");
			}
			
			if (claimsR.containsKey("type")) {
				
				String type = (String) claimsR.get("type");
				
				if (!type.equals("refresh-token")) {
					throw new JWTValidateException("Token inválido!");
				}
				
			}else {
				throw new JWTValidateException("Token inválido!");
			}
			
			Map<String,Object> claimsA = new HashMap<String,Object>();
			
			for (String key: claimsR.keySet() ) {
				
				if (key.startsWith("accessToken-")) {
					String keyAccess = key.replace("accessToken-", "");
					Object valueAccess = claimsR.get(key);
					claimsA.put(keyAccess, valueAccess);
				}
				
			}
			
			String id = (String) claimsA.get("jti");
			String iss = "postalis-api";
			String sub = (String) claimsA.get("sub");
			
			//Gerando novo accessToken
			String newAccessToken =  jwtBuilder.generateToken(jwtKeys.getPrivateKey(), id, sub, iss, jwtBuilder.getTokenTimeExpiration(),claimsA);  
			
			ResponseToken responseToken = new ResponseToken(newAccessToken, refreshToken);
			
			return responseToken;
			
			
		} catch (JWTValidateException e) {
			throw new JWTException(e.getCause());
		}
		
		
	}

	@Override
	public String getPublicKey() {
		
		return Base64.getEncoder().encodeToString(jwtKeys.getPublicKey().getEncoded());
		
	}

}
