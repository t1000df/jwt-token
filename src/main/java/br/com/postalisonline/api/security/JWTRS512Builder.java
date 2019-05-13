package br.com.postalisonline.api.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mapstruct.util.Experimental;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTRS512Builder {
	
	public static void main(String[] args) {

		/*
		 * System.out.println("generating keys"); Map<String, Object> rsaKeys = null;
		 * 
		 * try { rsaKeys = getRSAKeys(); } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * PublicKey publicKey = (PublicKey) rsaKeys.get("public"); PrivateKey
		 * privateKey = (PrivateKey) rsaKeys.get("private");
		 * 
		 * System.out.println("generated keys");
		 * 
		 * String id = "66664330172"; String sub = "alessandro.lima@4quality.com.br";
		 * String iss = "postalis-api";
		 * 
		 * String token = generateToken(privateKey, id, sub, iss, (5* 60* 1000));
		 * System.out.println("Generated Token:\n" + token);
		 * 
		 * Claims claims = verifyToken(token, publicKey);
		 */
        
    }
	
	public long getRefreshTokenTimeExpiration() {
		//tempo de expiração do refresh token 24 horas
		return (24 * 60 * 60 * 1000);
	}
	
	public long getTokenTimeExpiration() {
		//tempo de expiração do token 5 min
		return (5 * 60 * 1000);
	}

	// Get RSA keys. Uses key size of 2048.
    public Map<String, Object> getRSAKeys() throws Exception {
        
    	KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        
        Map<String, Object> keys = new HashMap<String, Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        
        return keys;
    }
    
    public String generateToken(PrivateKey privateKey, String id, String sub, String iss, long expirationMillis) {
    	
    	return generateToken(privateKey, id, sub, iss, expirationMillis);
    }
    
    public String generateToken(PrivateKey privateKey, String id, String sub, String iss, long expirationMillis,Map<String, Object> claims) {
        String token = null;
        try {
            
        	JwtBuilder builder = Jwts.builder()
            		.setId(id)
            		.setSubject(sub)
            		.setIssuer(iss)
            		.addClaims(claims)
            		.signWith(SignatureAlgorithm.RS512, privateKey);

            if (expirationMillis > 0) {
            	Date now = new Date();
            	expirationMillis += now.getTime();
            	Date dtExpiration = new Date(expirationMillis);
            	builder.setExpiration(dtExpiration);
            }
            
            token = builder.compact();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
    
    // verify and get claims using public key
    public Claims verifyToken(String token, PublicKey publicKey) {
        Claims claims;
        try {
        	
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();

        } catch (Exception e) {

            claims = null;
        }
        return claims;
    }

}
