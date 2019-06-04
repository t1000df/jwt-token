package br.com.postalisonline.identitymanager.api.service;

import br.com.postalisonline.identitymanager.api.entity.User;
import br.com.postalisonline.identitymanager.api.model.UserCredential;

public interface UserService {
	
	public User get(String id);
	
	public UserCredential findByCPF(String cpf);
	
	//public ResponseToken login(String cpf, String senha);

}
