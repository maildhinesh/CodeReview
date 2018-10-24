package com.kavinunlimited.aathichudi.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {
	
	private String token;
	
	private final UserDetails principal;

	public TokenBasedAuthentication(UserDetails principal) {
		super(principal.getAuthorities());
		this.principal = principal;
		// TODO Auto-generated constructor stub
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return token;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return principal;
	}
	
	@Override
	public boolean isAuthenticated() {
		return true;
	}

}
