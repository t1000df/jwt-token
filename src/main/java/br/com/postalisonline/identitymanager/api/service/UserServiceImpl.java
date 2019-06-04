package br.com.postalisonline.identitymanager.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.postalisonline.identitymanager.api.entity.User;
import br.com.postalisonline.identitymanager.api.model.UserCredential;
import br.com.postalisonline.identitymanager.api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Override
	public User get(String id) {
		
		User user = null;
		
		Optional<User> opUser = repository.findById(id);
		
		if (opUser.isPresent()) {
			user = opUser.get();
		}
		
		return user;
	}

	@Override
	public UserCredential findByCPF(String cpf) {
		
		return repository.findByCPF(cpf);
		
	}

}
