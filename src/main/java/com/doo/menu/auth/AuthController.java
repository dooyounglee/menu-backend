package com.doo.menu.auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doo.menu.menu.MenuService;
import com.doo.menu.user.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "*")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/all")
	public List<Auth> getAllAuths(Model model) {
		return authService.getAllAuths(); 
	}
	
	@PostMapping("/save")
	public Auth saveAuth(@RequestBody Auth auth) {
		System.out.println(auth);
		System.out.println(auth.getName());
		return authService.save(auth);
	}
	
	@DeleteMapping("/delete")
	public void deleteAuth(@RequestBody Auth auth) {
		authService.deleteAuth(auth);
	}
	
	@PostMapping("/menus")
	public void saveMenus(@RequestBody Map<String, Object> param) {
		Optional<Auth> oAuth = authService.getAuthById((String)param.get("auth"));
		if(oAuth.isPresent()) {
			Auth auth = oAuth.get();
			auth.setMenus((List<String>)param.get("arr"));
			authService.save(auth);
		}
	}
	
	@PostMapping("/users")
	public void saveUsers(@RequestBody Map<String, Object> param) {
		Optional<Auth> oAuth = authService.getAuthById((String)param.get("auth"));
		if(oAuth.isPresent()) {
			Auth auth = oAuth.get();
			auth.setUsers((List<String>)param.get("arr"));
			authService.save(auth);
		}
	}
}
