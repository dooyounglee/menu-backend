package com.doo.menu.menu;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String>{

	Menu findByName(String name);

	List<Menu> findByLevel(int level);
	
	List<Menu> findByLevelOrderByOrderAsc(int level);

	//List<Menu> findByLevelLessThanEqualOrderByLevelAscOrderAsc();
	List<Menu> findAllByOrderByLevelAscOrderAsc();
	
	void deleteById(String id);

}
