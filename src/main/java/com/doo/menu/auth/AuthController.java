package com.doo.menu.auth;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "*")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@GetMapping("/all")
	public List<Auth> getAllAuths(Model model) {
		return authService.getAllAuths(); 
	}
	
	@PostMapping("/save")
	public Auth saveAuth(@RequestBody Auth auth) {
		return authService.save(auth);
	}
	
	@DeleteMapping("/delete")
	public void deleteAuth(@RequestBody Auth auth) {
		authService.deleteAuth(auth);
	}
	
	@PostMapping("/menus")
	public void saveMenus(@RequestBody Map<String, Object> param) {
		authService.saveMenus(param);
	}
	
	@PostMapping("/users")
	public void saveUsers(@RequestBody Map<String, Object> param) {
		authService.saveUsers(param);
	}
}
