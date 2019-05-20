package br.com.postalisonline.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.postalisonline.api.entity.User;
import br.com.postalisonline.api.repository.UserRepository;

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

}
