package com.doo.menu.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		System.out.println(menu);
		menuService.save(menu);
		System.out.println(menu);
		
		if(menu.getParentId() != null) {
			Optional<Menu> oParent = menuService.getMenuById(menu.getParentId());
			if(oParent.isPresent()) {
				Menu parent = oParent.get();
				System.out.println(parent);
				List<Menu> collapse = parent.getCollapse();
				if(collapse == null) {
					collapse = new ArrayList<Menu>();
					collapse.add(menu);
					menuService.save(parent);
				} else {
					if(parent.getCollapse().indexOf(menu) == -1) {
						collapse.add(menu);
						collapse.sort((o1, o2) -> ((Menu)o1).getOrder() - ((Menu)o2).getOrder());
						//parent.setCollapse(collapse);
						//System.out.println(parent);
						menuService.save(parent);
					}
				}
			}
		}
	}
	
	@DeleteMapping("/delete")
	public void deleteMenu(@RequestBody Menu menu) {
		System.out.println(menu);
		
		if(menu.getParentId() != null) {
			Optional<Menu> oParent = menuService.getMenuById(menu.getParentId());
			if(oParent.isPresent()) {
				Menu parent = oParent.get();
				System.out.println(parent);
				List<Menu> collapse = parent.getCollapse();
				//System.out.println(collapse);
				collapse.remove(menu);
				//System.out.println(collapse);
				System.out.println(parent);
				menuService.save(parent);
			}
		}
		menuService.deleteMenu(menu.getId());
	}
}
