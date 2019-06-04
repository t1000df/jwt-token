package br.com.postalisonline.identitymanager.bean;

/**
 * 
 * @author alessandro.lima@4quality
 *
 */
public class ResponsePublicKey {
	
	public ResponsePublicKey() {
		
	}
	
	public ResponsePublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	private String publicKey;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	
}
