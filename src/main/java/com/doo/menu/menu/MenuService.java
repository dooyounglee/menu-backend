package com.doo.menu.menu;

import java.util.ArrayList;
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

	public void save(Menu menu) {
		
		menuRepository.save(menu);
		
		if(menu.getParentId() != null) {
			Optional<Menu> oParent = menuRepository.findById(menu.getParentId());
			if(oParent.isPresent()) {
				Menu parent = oParent.get();
				List<Menu> collapse = parent.getCollapse();
				if(collapse == null) {
					collapse = new ArrayList<Menu>();
					collapse.add(menu);
					menuRepository.save(parent);
				} else {
					if(parent.getCollapse().indexOf(menu) == -1) {
						collapse.add(menu);
						collapse.sort((o1, o2) -> ((Menu)o1).getOrder() - ((Menu)o2).getOrder());
						menuRepository.save(parent);
					}
				}
			}
		}
	}

	public Optional<Menu> getMenuById(String id) {
		return menuRepository.findById(id);
	}

	public void deleteMenu(Menu menu) {
		
		if(menu.getParentId() != null) {
			Optional<Menu> oParent = menuRepository.findById(menu.getParentId());
			if(oParent.isPresent()) {
				Menu parent = oParent.get();
				List<Menu> collapse = parent.getCollapse();
				collapse.remove(menu);
				menuRepository.save(parent);
			}
		}
		menuRepository.deleteById(menu.getId());
	}
}
