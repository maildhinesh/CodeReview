package com.kavinunlimited.aathichudi.domain.response;

import com.kavinunlimited.aathichudi.dao.entity.enums.RegistrationStatus;

public class ValidateRegistrationLinkResponse extends TamilSchoolResponse {
	
	private boolean newUser;
	
	private RegistrationStatus registrationStatus;
	
	public ValidateRegistrationLinkResponse() {
		this.newUser = true;
		this.setSuccess(true);
	}
	
	public ValidateRegistrationLinkResponse(boolean newUser) {
		this.newUser = newUser;
		this.setSuccess(true);
	}

	public boolean isNewUser() {
		return newUser;
	}

	public ValidateRegistrationLinkResponse setNewUser(boolean newUser) {
		this.newUser = newUser;
		return this;
	}

	public RegistrationStatus getRegistrationStatus() {
		return registrationStatus;
	}

	public ValidateRegistrationLinkResponse setRegistrationStatus(RegistrationStatus registrationStatus) {
		this.registrationStatus = registrationStatus;
		return this;
	}

}
