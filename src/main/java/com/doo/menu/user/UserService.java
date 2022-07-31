package com.doo.menu.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(String id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public Optional<User> signIn(User user) {
		return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
	}
	
	

}