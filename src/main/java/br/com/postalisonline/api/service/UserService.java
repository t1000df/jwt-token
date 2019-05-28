package br.com.postalisonline.api.service;

import br.com.postalisonline.api.entity.User;
import br.com.postalisonline.api.model.UserCredential;

public interface UserService {
	
	public User get(String id);
	
	public UserCredential findByCPF(String cpf);
	
	//public ResponseToken login(String cpf, String senha);

}
