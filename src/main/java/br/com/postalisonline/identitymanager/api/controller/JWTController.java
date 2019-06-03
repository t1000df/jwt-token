package br.com.postalisonline.identitymanager.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.postalisonline.identitymanager.api.service.JWTException;
import br.com.postalisonline.identitymanager.api.service.JWTService;
import br.com.postalisonline.identitymanager.api.service.UserService;
import br.com.postalisonline.identitymanager.bean.RequestRefreshToken;
import br.com.postalisonline.identitymanager.bean.RequestToken;
import br.com.postalisonline.identitymanager.bean.ResponsePublicKey;
import br.com.postalisonline.identitymanager.bean.ResponseToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * 
 * @author alessandro.lima@4quality.com.br
 *
 */
@RestController
@RequestMapping("/v1/jwt")
public class JWTController {
	
	private static Logger logger = LoggerFactory.getLogger(JWTController.class);
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	UserService userService;

	@PostMapping("token")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Token gerado com sucesso.", response = ResponseToken.class),
        @ApiResponse(code = 412, message = "Dados inválidos para geração."),
	})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseToken> token(@RequestBody RequestToken requestToken) {
		
		ResponseToken responseToken = jwtService.generate(requestToken);
		
		if (responseToken == null) {
			return new ResponseEntity<ResponseToken>(HttpStatus.PRECONDITION_FAILED);
		}
		
		return new ResponseEntity<ResponseToken>(responseToken, HttpStatus.CREATED);
		
	}
	
	@GetMapping("fake")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="Cria um token fake para testes.")
	public String fakeToken() {
		
		return jwtService.generateFake();
	}
	
	@GetMapping("validate")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Token válido."),
        @ApiResponse(code = 412, message = "Token inválido ou expirado."),
	})
	public ResponseEntity<String> validate(String token) {
		
		try {
			jwtService.validate(token);
			return new ResponseEntity<String>("Token válido.",HttpStatus.OK);
		} catch (JWTException e) {
			logger.error(e.getLocalizedMessage(), e.getCause());
			return new ResponseEntity<String>(e.getLocalizedMessage(),HttpStatus.PRECONDITION_FAILED);
		}
		
	}
	
	@PostMapping("refresh")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Token de acesso gerado.", response = ResponseToken.class),
        @ApiResponse(code = 412, message = "Token de atualização inválido ou expirado."),
	})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseToken> refresh(@RequestBody RequestRefreshToken refreshToken) {
		
		try {
			ResponseToken responseToken = jwtService.refresh(refreshToken.getRefreshToken());
			return new ResponseEntity<ResponseToken>(responseToken,HttpStatus.CREATED);
		} catch (JWTException e) {
			logger.error(e.getLocalizedMessage(), e.getCause());
			return new ResponseEntity<ResponseToken>(HttpStatus.PRECONDITION_FAILED);
		}
		
	}
	
	@GetMapping("public")
	public ResponseEntity<ResponsePublicKey> recuperarChavePublica() {
		
		try {
			ResponsePublicKey responsePublicKey = new ResponsePublicKey(jwtService.getPublicKey());
			return new ResponseEntity<ResponsePublicKey>(responsePublicKey,HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e.getCause());
			return new ResponseEntity<ResponsePublicKey>(HttpStatus.PRECONDITION_FAILED);
		}
		
	}
	
	

}
