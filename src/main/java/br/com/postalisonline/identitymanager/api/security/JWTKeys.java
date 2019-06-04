package br.com.postalisonline.identitymanager.api.security;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.stereotype.Component;

/**
 * 
 * @author alessandro.lima@4quality
 *
 */

@Component
public class JWTKeys {
	
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	
}
