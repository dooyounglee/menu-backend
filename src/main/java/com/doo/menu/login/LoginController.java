package com.doo.menu.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import com.doo.menu.user.User;
import com.doo.menu.user.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials, HttpSession session){
		
		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
		
		Authentication auth = authenticationManager.authenticate(creds);
		
		String jwts = jwtService.getToken(auth.getName());
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Optional<User> oUser = userService.findByEmail(credentials.getEmail());
		if(oUser.isPresent()) {
			User user = oUser.get();
			session.setAttribute("user", user);
			returnMap.put("user", user);
		} else {
			returnMap.put("message", "no one");
		}
		
		returnMap.put("Authorization", jwts);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				//.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "accessToken")
				.body(returnMap);
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> getUser(HttpSession session){
		
		User user = (User)session.getAttribute("user");
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("user", user);
		
		return ResponseEntity.ok()
				.body(returnMap);
	}
}
