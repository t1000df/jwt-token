package br.com.postalisonline.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.postalisonline.api.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	public User findByEmail(String email);

}
