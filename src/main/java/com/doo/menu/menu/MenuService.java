package com.doo.menu.menu;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;
	
	public List<Menu> getAllMenus(){
		return menuRepository.findAll();
	}
	
	public Menu setMenu(Menu menu) {
		return menuRepository.save(menu);
	}
	
	public Menu getMenuByName(String name) {
		return menuRepository.findByName(name);
	}

	public List<Menu> getAllMenusWhereLevel(int level) {
		return menuRepository.findByLevel(level);
	}
	
	public List<Menu> getAllMenusWhereLevelOrderByOrderAsc(int level) {
		return menuRepository.findByLevelOrderByOrderAsc(level);
	}

	public List<Menu> getAllMenusBasic() {
		return menuRepository.findAllByOrderByLevelAscOrderAsc();
	}

	public Menu save(Menu menu) {
		return menuRepository.save(menu);
	}

	public Optional<Menu> getMenuById(String id) {
		return menuRepository.findById(id);
	}

	public void deleteMenu(String id) {
		menuRepository.deleteById(id);
	}
}
