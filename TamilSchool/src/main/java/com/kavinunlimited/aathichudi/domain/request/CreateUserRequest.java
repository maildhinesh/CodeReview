package com.kavinunlimited.aathichudi.domain.request;

import javax.validation.constraints.NotNull;

public class CreateUserRequest {
	
	@NotNull
	private String token;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	private String confirmPassword;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
