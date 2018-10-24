package com.kavinunlimited.aathichudi.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kavinunlimited.aathichudi.dao.entity.Role;
import com.kavinunlimited.aathichudi.dao.entity.User;
import com.kavinunlimited.aathichudi.dao.entity.enums.UserStatus;

public class LoggedInUser implements UserDetails {
	
	private User user;
	
	public LoggedInUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for(Role role: user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return (this.user.getStatus().equals(UserStatus.ACTIVE))? true: false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return (this.user.getStatus().equals(UserStatus.LOCKED))? false : true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return (this.user.getStatus().equals(UserStatus.ACTIVE)) ? true : false;
	}

}
