package br.com.postalisonline.identitymanager.bean;

import java.util.Map;

/**
 * 
 * @author alessandro.lima@4quality
 *
 */
public class RequestAPIToken {
	
	private String api;
	private String[] scope;
	private Map<String,Object> claims;
	
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String[] getScope() {
		return scope;
	}
	public void setScope(String[] scope) {
		this.scope = scope;
	}
	public Map<String,Object> getClaims() {
		return claims;
	}
	public void setClaims(Map<String,Object> claims) {
		this.claims = claims;
	}
	

}
