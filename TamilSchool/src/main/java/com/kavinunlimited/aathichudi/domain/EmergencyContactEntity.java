package com.kavinunlimited.aathichudi.domain;

import com.kavinunlimited.aathichudi.dao.entity.EmergencyContact;

public class EmergencyContactEntity {
	
	private String contactName;
	
	private String relationship;
	
	private String homePhone;
	
	private String cellPhone;

	public String getContactName() {
		return contactName;
	}

	public String getRelationship() {
		return relationship;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public EmergencyContactEntity setContactName(String contactName) {
		this.contactName = contactName;
		return this;
	}

	public EmergencyContactEntity setRelationship(String relationship) {
		this.relationship = relationship;
		return this;
	}

	public EmergencyContactEntity setHomePhone(String homePhone) {
		this.homePhone = homePhone;
		return this;
	}

	public EmergencyContactEntity setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
		return this;
	}
	
	
	public EmergencyContactEntity map(EmergencyContact emergencyContact) {
		return this.setContactName(emergencyContact.getFullName())
					.setRelationship(emergencyContact.getRelationship())
					.setCellPhone(emergencyContact.getCellPhoneNumber())
					.setHomePhone(emergencyContact.getHomePhoneNumber());
	}
	
}
