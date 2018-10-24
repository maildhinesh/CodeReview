package com.kavinunlimited.aathichudi.domain.request;

import javax.validation.constraints.NotNull;

public class CreateParentLoginRequest {
	
	@NotNull
	private String registrationId;
	
	@NotNull
	private String password;

	public String getRegistrationId() {
		return registrationId;
	}

	public String getPassword() {
		return password;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
