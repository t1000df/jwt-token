package br.com.postalisonline.api.bean;

/**
 * 
 * @author alessandro.lima@4quality.com.br
 *
 */
public class RequestToken {
	
	private String user;
	private String email;
	private String password;
	private String scope;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

	
}
