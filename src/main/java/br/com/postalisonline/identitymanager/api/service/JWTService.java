package br.com.postalisonline.identitymanager.api.service;

import br.com.postalisonline.identitymanager.bean.RequestToken;
import br.com.postalisonline.identitymanager.bean.ResponseToken;

/**
 * 
 * @author alessandro.lima@4quality.com.br
 *
 */
public interface JWTService {
	
	public ResponseToken generate(RequestToken request) ;
	
	public String generateFake();
	
	public void validate(String token) throws JWTException;
	
	public ResponseToken refresh(String refreshtoken) throws JWTException;
	
	public String getPublicKey() ;

}
