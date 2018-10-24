package com.kavinunlimited.aathichudi.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.kavinunlimited.aathichudi.dao.entity.EmergencyContact;
import com.kavinunlimited.aathichudi.dao.entity.Parent;
import com.kavinunlimited.aathichudi.dao.entity.Student;
import com.kavinunlimited.aathichudi.dao.entity.enums.Sex;

public class StudentEntity {
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private Sex sex;
	
	private LocalDate dateOfBirth;
	
	private AddressEntity address;
	
	private List<ParentEntity> parents;
	
	private List<EmergencyContactEntity> emergencyContacts;
	
	private InsuranceInfoEntity insuranceInfo;
	
	private boolean insuranceConsent;
	
	private LocalDate insuranceConsentDate;
	
	private boolean photoConsent;
	
	private LocalDate photoConsentDate;

	public String getId() {
		return id;
	}

	public StudentEntity setId(String id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Sex getSex() {
		return sex;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public StudentEntity setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public StudentEntity setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public StudentEntity setSex(Sex sex) {
		this.sex = sex;
		return this;
	}

	public StudentEntity setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public StudentEntity setAddress(AddressEntity address) {
		this.address = address;
		return this;
	}

	public List<ParentEntity> getParents() {
		return parents;
	}

	public StudentEntity setParents(List<ParentEntity> parents) {
		this.parents = parents;
		return this;
	}
	
	public StudentEntity addParents(ParentEntity parent) {
		if(this.parents == null) { 
			this.parents = new ArrayList<ParentEntity>();
		}
		this.parents.add(parent);
		return this;
	}

	public List<EmergencyContactEntity> getEmergencyContacts() {
		return emergencyContacts;
	}

	public InsuranceInfoEntity getInsuranceInfo() {
		return insuranceInfo;
	}

	public boolean isInsuranceConsent() {
		return insuranceConsent;
	}

	public LocalDate getInsuranceConsentDate() {
		return insuranceConsentDate;
	}

	public boolean isPhotoConsent() {
		return photoConsent;
	}

	public LocalDate getPhotoConsentDate() {
		return photoConsentDate;
	}

	public StudentEntity setEmergencyContacts(List<EmergencyContactEntity> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
		return this;
	}
	
	public StudentEntity addEmergencyContact(EmergencyContactEntity emergencyContact) {
		if(this.emergencyContacts == null) {
			this.emergencyContacts = new ArrayList<EmergencyContactEntity>();
		}
		this.emergencyContacts.add(emergencyContact);
		return this;
	}

	public StudentEntity setInsuranceInfo(InsuranceInfoEntity insuranceInfo) {
		this.insuranceInfo = insuranceInfo;
		return this;
	}

	public StudentEntity setInsuranceConsent(boolean insuranceConsent) {
		this.insuranceConsent = insuranceConsent;
		return this;
	}

	public StudentEntity setInsuranceConsentDate(LocalDate insuranceConsentDate) {
		this.insuranceConsentDate = insuranceConsentDate;
		return this;
	}

	public StudentEntity setPhotoConsent(boolean photoConsent) {
		this.photoConsent = photoConsent;
		return this;
	}

	public StudentEntity setPhotoConsentDate(LocalDate photoConsentDate) {
		this.photoConsentDate = photoConsentDate;
		return this;
	}
	
	public StudentEntity map(Student student) {
		this.setFirstName(student.getFirstName())
			.setLastName(student.getLastName())
			.setId(student.getId())
			.setDateOfBirth(student.getDateOfBirth())
			.setSex(student.getSex())
			.setInsuranceInfo((student.getInsuranceInfo() != null) ? new InsuranceInfoEntity().map(student.getInsuranceInfo()) : null)
			.setInsuranceConsent(student.isInsuranceConsent())
			.setInsuranceConsentDate(student.getInsuranceConsentDate())
			.setPhotoConsent(student.isPhotoConsent())
			.setAddress((student.getAddress() != null) ? new AddressEntity().map(student.getAddress()) : null)
			.setPhotoConsentDate(student.getPhotoConsentDate());
		if(this.emergencyContacts != null && this.emergencyContacts.size() > 0 ) {
			this.emergencyContacts.clear();
		}
		if(student.getEmergencyContacts() != null) {
			for(EmergencyContact emergencyContact : student.getEmergencyContacts()) {
				this.addEmergencyContact(new EmergencyContactEntity().map(emergencyContact));
			}
		}
		if(this.parents != null && this.parents.size() > 0) {
			this.parents.clear();
		}
		if(student.getParents() != null) {
			for(Parent parent: student.getParents()) {
				this.addParents(new ParentEntity().map(parent));
			}
		}
		return this;
	}
	
}
