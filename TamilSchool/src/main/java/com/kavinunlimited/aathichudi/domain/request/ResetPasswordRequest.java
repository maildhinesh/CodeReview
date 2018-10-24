package com.kavinunlimited.aathichudi.domain.request;

import javax.validation.constraints.NotNull;

public class ResetPasswordRequest {
	
	@NotNull
	private String token;
	
	@NotNull
	private String newPassword;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
