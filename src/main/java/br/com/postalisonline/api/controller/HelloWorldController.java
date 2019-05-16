package br.com.postalisonline.api.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postalisonline.api.bean.Message;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/hello")
public class HelloWorldController {
	
	
	@ApiOperation("Exibe uma mensagem de \"Hello World !!!\"")
	@GetMapping("world")
	public String helloWorld() {
 
		return "Hello World !!!!" ;
	}
	
	@ApiOperation("Retorna um JSON de teste.")
	@GetMapping("message")
	@Produces(MediaType.APPLICATION_JSON)
	public Message message() {
 
		return new Message("Hello World !!!!") ;
	}

}
