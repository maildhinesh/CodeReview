package com.kavinunlimited.aathichudi.domain.request;

import com.kavinunlimited.aathichudi.dao.entity.Role;

public class UserRegistrationRequest {
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private Role role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
