package br.com.postalisonline.api.controller;

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

import br.com.postalisonline.api.bean.RequestToken;
import br.com.postalisonline.api.bean.ResponsePublicKey;
import br.com.postalisonline.api.bean.ResponseToken;
import br.com.postalisonline.api.service.JWTException;
import br.com.postalisonline.api.service.JWTService;
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

	@PostMapping("token")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Token gerado com sucesso."),
        @ApiResponse(code = 412, message = "Dados inválidos para geração."),
	})
	public ResponseEntity<ResponseToken> token(@RequestBody RequestToken requestToken) {
		
		ResponseToken responseToken = jwtService.generate(requestToken);
		
		ResponseEntity<ResponseToken> responseEntity = new ResponseEntity<ResponseToken>(responseToken, HttpStatus.OK);
		
		return responseEntity;
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
	
	@GetMapping("refresh")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Token de acesso gerado."),
        @ApiResponse(code = 412, message = "Token de atualização inválido ou expirado."),
	})
	public ResponseEntity<ResponseToken> refresh(String refreshToken) {
		
		try {
			ResponseToken responseToken = jwtService.refresh(refreshToken);
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
