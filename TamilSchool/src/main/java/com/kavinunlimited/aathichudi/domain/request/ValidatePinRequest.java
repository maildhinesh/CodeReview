package com.kavinunlimited.aathichudi.domain.request;

import javax.validation.constraints.NotNull;

public class ValidatePinRequest {
	
	@NotNull
	private String pin;
	
	@NotNull
	private String token;
	
	@NotNull
	private String email;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

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

}
