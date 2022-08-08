package com.doo.menu.security;

import lombok.Data;

@Data
public class AccountCredentials {

	private String username;
	private String password;
	
	private String email;
}
