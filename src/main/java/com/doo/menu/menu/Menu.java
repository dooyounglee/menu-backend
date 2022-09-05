package com.doo.menu.menu;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "menu")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

	@Id private String id;         // ObjectId
	private String name;           // 1단계 메뉴명
	private int level;             // 메뉴레벨 0:단일메뉴 1:최상위메뉴 2,3,4,,,,
	private int order;             
	private String parentId;       
	private String icon;           // 1단계메뉴에 보일 icon
	private String description;    // 2단계메뉴 아래첨자로 부연설명
	private int columns;           
	private int rowsPerColumn;     
	private String route;          
	private String component;      
	private boolean dropdown;      // 하위메뉴 여부
	private String delYn;          // 삭제여부
	
	@DocumentReference()
	private List<Menu> collapse;   // 하위메뉴 배열
	
	private String href; //1단계메뉴 링크
	
	public List<Menu> getCollapse() {
		if (this.collapse == null || this.collapse.size() == 0) {
			return null;
		} else {
			return this.collapse;
		}
	}
	
}
