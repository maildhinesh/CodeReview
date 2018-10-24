package com.kavinunlimited.aathichudi.dao.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kavinunlimited.aathichudi.dao.entity.enums.UserStatus;

public class User {
	
	@Id
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private LocalDate createdDate;
	
	@DBRef
	private List<Role> roles;
	
	private UserStatus status;
	
	@DBRef(lazy = true)
	private Parent parent;
	
	public String getId() {
		return id;
	}

	public User setId(String id) {
		this.id = id;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public User setRoles(List<Role> roles) {
		this.roles = roles;
		return this;
	}
	
	public User addRole(Role role) {
		if(this.roles == null) {
			roles = new ArrayList<Role>();
		}
		roles.add(role);
		return this;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public User setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public UserStatus getStatus() {
		return status;
	}

	public User setStatus(UserStatus status) {
		this.status = status;
		return this;
	}

	public Parent getParent() {
		return parent;
	}

	public User setParent(Parent parent) {
		this.parent = parent;
		return this;
	}
	
}
