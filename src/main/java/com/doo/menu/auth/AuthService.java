package com.doo.menu.auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doo.menu.user.User;

@Service
public class AuthService {

	@Autowired
	private AuthRepository authRepository;
	
	public List<Auth> getAllAuths() {
		return authRepository.findAll();
	}
	
	public Auth save(Auth auth) {
		return authRepository.save(auth);
	}

	public void saveMenus(Map<String, Object> param) {
		Optional<Auth> oAuth = authRepository.findById((String)param.get("auth"));
		if(oAuth.isPresent()) {
			Auth auth = oAuth.get();
			auth.setMenus((List<String>)param.get("arr"));
			authRepository.save(auth);
		}
	}
	
	public void saveUsers(Map<String, Object> param) {
		Optional<Auth> oAuth = authRepository.findById((String)param.get("auth"));
		if(oAuth.isPresent()) {
			Auth auth = oAuth.get();
			auth.setUsers((List<String>)param.get("arr"));
			authRepository.save(auth);
		}
	}
	
	public void deleteAuth(Auth auth) {
		authRepository.deleteById(auth.getId());
	}

	public List<Auth> getAuthByUserId(User user) {
		return authRepository.findByUsersContaining(user.getId());
	}

}
