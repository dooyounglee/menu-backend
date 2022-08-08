package com.doo.menu.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

import com.doo.menu.auth.AuthService;
import com.doo.menu.menu.MenuService;
import com.doo.menu.security.AccountCredentials;
import com.doo.menu.security.JwtService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("/all")
	public List<User> getAllUsers(Model model) {
		return userService.getAllUsers(); 
	}
	
	/* @PostMapping("/signIn")
	public Map<String, Object> signInPost(@RequestBody User user, Model model) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Optional<User> oUser = userService.signIn(user);
		if(oUser.isPresent()) {
			user = oUser.get();
			returnMap.put("user", user);
			returnMap.put("authList", authService.getAuthByUserId(user));
		} else {
			returnMap.put("message", "no one");
		}
		
		returnMap.put("menus", menuService.getAllMenusWhereLevelOrderByOrderAsc(1));
		return returnMap;
	} */
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials){
		
		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
		
		Authentication auth = authenticationManager.authenticate(creds);
		
		String jwts = jwtService.getToken(auth.getName());
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Optional<User> oUser = userService.findByEmail(credentials.getEmail());
		if(oUser.isPresent()) {
			User user = oUser.get();
			returnMap.put("user", user);
			returnMap.put("authList", authService.getAuthByUserId(user));
		} else {
			returnMap.put("message", "no one");
		}
		
		returnMap.put("menus", menuService.getAllMenusWhereLevelOrderByOrderAsc(1));
		returnMap.put("accessToken", jwts);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				.body(returnMap);
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String password = user.getPassword();
		String encoded = encoder.encode(password);
		user.setPassword(encoded);
		
		user.setRole("USER");
		
		userService.save(user);
		
		return ResponseEntity.ok()
				.build();
	}
}
