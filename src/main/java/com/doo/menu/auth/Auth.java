package com.doo.menu.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "auth")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auth {

	@Id private String id;
	private boolean isDel;
	private String name;
	private String description;
	
	@Builder.Default
	private List<String> menus = new ArrayList<>();
	
	@Builder.Default
	private List<String> users = new ArrayList<>();
}
