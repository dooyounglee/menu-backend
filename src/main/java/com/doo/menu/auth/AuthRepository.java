package com.doo.menu.auth;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends MongoRepository<Auth, String>{

	List<Auth> findByUsersContaining(String id);

}
