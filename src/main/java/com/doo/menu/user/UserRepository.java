package com.doo.menu.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	Optional<User> findByEmailAndPassword(String email, String password);

}
