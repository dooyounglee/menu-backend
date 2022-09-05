package com.doo.menu.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@CrossOrigin(originPatterns = "*")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@GetMapping("/all")
	public List<Menu> getAllMenus(Model model) {
		return menuService.getAllMenus(); 
	}
	
	@GetMapping("/basic")
	public List<Menu> getAllMenusBasic(Model model) {
		return menuService.getAllMenusWhereLevelOrderByOrderAsc(1); 
	}
	
	@GetMapping("/level/{level}")
	public List<Menu> getAllMenus(@PathVariable(name = "level") int level, Model model) {
		return menuService.getAllMenusWhereLevel(level); 
	}
	
	@PostMapping("/save")
	public void postSave(@RequestBody Menu menu) {
		menuService.save(menu);
	}
	
	@DeleteMapping("/delete")
	public void deleteMenu(@RequestBody Menu menu) {
		menuService.deleteMenu(menu);
	}
}
