package com.doo.menu.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doo.menu.auth.AuthService;
import com.doo.menu.menu.MenuService;

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
	
	@PostMapping("/signIn")
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
	}
}
