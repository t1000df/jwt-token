package br.com.postalisonline.identitymanager.api.security;

import java.util.Date;
import java.util.Map;

public class JWTData {
	
	
	private String id;
	private String subject;
	private String issuer;
	private Date expiration;
	
	private Map<String,Object> claims;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Map<String, Object> getClaims() {
		return claims;
	}

	public void setClaims(Map<String, Object> claims) {
		this.claims = claims;
	}
}