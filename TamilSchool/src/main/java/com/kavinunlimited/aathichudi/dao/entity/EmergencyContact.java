package com.kavinunlimited.aathichudi.dao.entity;

import org.springframework.data.annotation.Id;

import com.kavinunlimited.aathichudi.domain.EmergencyContactEntity;

public class EmergencyContact {
	
	private String fullName;
	
	private String relationship;
	
	private String homePhoneNumber;
	
	private String cellPhoneNumber;

	public String getFullName() {
		return fullName;
	}

	public EmergencyContact setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getRelationship() {
		return relationship;
	}

	public EmergencyContact setRelationship(String relationship) {
		this.relationship = relationship;
		return this;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public EmergencyContact setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
		return this;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public EmergencyContact setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
		return this;
	}
	
	public EmergencyContact map(EmergencyContactEntity emergencyContact) {
		return this.setFullName(emergencyContact.getContactName())
				.setRelationship(emergencyContact.getRelationship())
				.setCellPhoneNumber(emergencyContact.getCellPhone())
				.setHomePhoneNumber(emergencyContact.getHomePhone());
	}

}
