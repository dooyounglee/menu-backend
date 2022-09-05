package com.doo.menu.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doo.menu.auth.Auth;
import com.doo.menu.auth.AuthService;
import com.doo.menu.menu.MenuService;
import com.doo.menu.security.AccountCredentials;
import com.doo.menu.security.JwtService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {
	
	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;
	
	private final MenuService menuService;
	
	private final AuthService authService;
	
	@GetMapping("/all")
	public List<User> getAllUsers(Model model) {
		return userService.getAllUsers(); 
	}
	
	private final JwtService jwtService;
	
	final AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials){
		
		String jwts = null;
		
		try {
			UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
			Authentication auth = authenticationManager.authenticate(creds);
			jwts = jwtService.getToken(auth.getName());
		} catch (BadCredentialsException e) {// email없을때 or email있는데 password아닐때
			return ResponseEntity.badRequest()
					.body("Invalid email or password");
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		User user = userService.findByEmail(credentials.getEmail()).get();
		
		List<Auth> authList = authService.getAuthByUserId(user);
		if (authList.size() == 0) {
			return ResponseEntity.badRequest()
					.body("no auth");
		}
		
		// authList들에 연결된 menu가 없을때
		if (authList.stream().filter(a -> a.getMenus().size() > 0).count() == 0) {
			return ResponseEntity.badRequest()
					.body("no auth");
		};
		
		returnMap.put("authList", authList);
		returnMap.put("menus", menuService.getAllMenusWhereLevelOrderByOrderAsc(1));
		returnMap.put("accessToken", jwts);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				.body(returnMap);
	}
	
	final BCryptPasswordEncoder encoder;
	
	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody User user) {
		logger.debug("com.doo.menu.user.UserController.signUp.user : {}", user);
		
		// 이미 가입된 email이 있는지
		int count = userService.countByEmail(user.getEmail());
		logger.debug("com.doo.menu.user.UserController.signUp.count : {}", count);
		if (count > 0) {
			return ResponseEntity.badRequest()
					.body("This email already exists.");
		}
		
		String password = user.getPassword();
		String encoded = encoder.encode(password);
		user.setPassword(encoded);
		
		user.setRole("USER");
		
		userService.save(user);
		
		return ResponseEntity.ok()
				.build();
	}
}
