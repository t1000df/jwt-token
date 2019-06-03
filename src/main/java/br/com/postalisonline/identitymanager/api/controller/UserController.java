package br.com.postalisonline.identitymanager.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postalisonline.identitymanager.api.entity.User;
import br.com.postalisonline.identitymanager.api.model.UserCredential;
import br.com.postalisonline.identitymanager.api.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/user")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@GetMapping("{id}")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Usuário recuperado."),
        @ApiResponse(code = 204, message = "Usuário não encontrado."),
	})
	public ResponseEntity<User> getUsuarioById(@PathVariable("id") String id) {
		
		try {
			
			User user = userService.get(id);
			
			if (user == null) {
				throw new Exception("Usuário não encontrado.");
			}
			
			return new ResponseEntity<User>(user,HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e.getCause());
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("cpf/{cpf}")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Usuário recuperado."),
        @ApiResponse(code = 204, message = "Usuário não encontrado."),
	})
	public ResponseEntity<UserCredential> getUsuarioByCPF(@PathVariable("cpf") String cpf) {
		
		try {
			
			UserCredential credential = userService.findByCPF(cpf);
			
			if (credential == null) {
				throw new Exception("Usuário não encontrado.");
			}
			
			return new ResponseEntity<UserCredential>(credential,HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e.getCause());
			return new ResponseEntity<UserCredential>(HttpStatus.NO_CONTENT);
		}
		
	}

}
