package com.kavinunlimited.aathichudi.domain.response;

import java.util.List;

import com.kavinunlimited.aathichudi.dao.entity.Registration;

public class GetAllRegistrationsResponse extends TamilSchoolResponse {
	
	private List<Registration> registrations;
	
	public GetAllRegistrationsResponse() {
		super.setSuccess(true);
	}

	public List<Registration> getRegistrations() {
		return registrations;
	}

	public GetAllRegistrationsResponse setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
		return this;
	}

}
