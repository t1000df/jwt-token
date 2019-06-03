package br.com.postalisonline.identitymanager.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEB_SSE_SENHA", schema="dbo")
public class User {

	@Id
	@Column(name="CD_USUARIO")
	private String id;
	
	@Column(name="TX_SENHA_CRIPTO")
	private String senha;
	
	@Column(name="EMAIL_CORPORATIVO")
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	
}
