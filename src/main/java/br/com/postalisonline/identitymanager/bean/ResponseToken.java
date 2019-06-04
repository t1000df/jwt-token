package br.com.postalisonline.identitymanager.bean;

/**
 * 
 * @author alessandro.lima@4quality.com.br
 *
 */
public class ResponseToken {
	
	public ResponseToken() {
		
	}
	
	public ResponseToken(String accessToken, String refreshToken) {
		this.accessToken =accessToken;
		this.refreshToken = refreshToken;
	}
	
	private String accessToken;
	private String refreshToken;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	

}
