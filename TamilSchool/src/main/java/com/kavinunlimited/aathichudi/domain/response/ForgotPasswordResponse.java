package com.kavinunlimited.aathichudi.domain.response;

public class ForgotPasswordResponse extends TamilSchoolResponse {
	
	private String token;

	public String getToken() {
		return token;
	}

	public ForgotPasswordResponse setToken(String token) {
		this.token = token;
		return this;
	}

}
