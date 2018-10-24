package com.kavinunlimited.aathichudi.domain.response;

import java.util.ArrayList;
import java.util.List;

import com.kavinunlimited.aathichudi.dao.entity.Role;

public class UserProfileResponse extends TamilSchoolResponse {
	
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public UserProfileResponse setRoles(List<Role> roles) {
		this.roles = roles;
		return this;
	}
	
	public UserProfileResponse addRole(Role role) {
		if(this.roles == null) {
			this.roles = new ArrayList<Role> ();
		}
		this.roles.add(role);
		return this;
	}

}
