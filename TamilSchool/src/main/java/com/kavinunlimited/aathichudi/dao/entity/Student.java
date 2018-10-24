package com.kavinunlimited.aathichudi.dao.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kavinunlimited.aathichudi.dao.entity.enums.Sex;
import com.kavinunlimited.aathichudi.domain.EmergencyContactEntity;
import com.kavinunlimited.aathichudi.domain.ParentEntity;
import com.kavinunlimited.aathichudi.domain.StudentEntity;

public class Student {
	
	@Id
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private LocalDate dateOfBirth;
	
	private Sex sex;
	
	@DBRef(lazy = true)
	@JsonBackReference
	private List<Parent> parents;
	
	@DBRef(lazy = true)
	@JsonBackReference
	private Registration currentRegistration;
	
	List<EmergencyContact> emergencyContacts;
	
	private InsuranceInfo insuranceInfo;
	
	private boolean insuranceConsent;
	
	private LocalDate insuranceConsentDate;
	
	private boolean photoConsent;
	
	private LocalDate photoConsentDate;
	
	private Address address;

	public String getFirstName() {
		return firstName;
	}

	public String getId() {
		return id;
	}

	public Student setId(String id) {
		this.id = id;
		return this;
	}

	public Student setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Student setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public Student setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public Sex getSex() {
		return sex;
	}

	public Student setSex(Sex sex) {
		this.sex = sex;
		return this;
	}

	public List<Parent> getParents() {
		return parents;
	}

	public Student setParents(List<Parent> parents) {
		this.parents = parents;
		return this;
	}
	
	public Student addParent(Parent parent) {
		if(this.parents == null) {
			this.parents = new ArrayList<Parent>();
		}
		this.parents.add(parent);
		return this;
	}

	public List<EmergencyContact> getEmergencyContacts() {
		return emergencyContacts;
	}

	public Student setEmergencyContacts(List<EmergencyContact> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
		return this;
	}
	
	public Student addEmergencyContact(EmergencyContact emergencyContact) {
		if(this.emergencyContacts == null) {
			this.emergencyContacts = new ArrayList<EmergencyContact>();
		}
		this.emergencyContacts.add(emergencyContact);
		return this;
	}

	public InsuranceInfo getInsuranceInfo() {
		return insuranceInfo;
	}

	public Student setInsuranceInfo(InsuranceInfo insuranceInfo) {
		this.insuranceInfo = insuranceInfo;
		return this;
	}

	public Registration getCurrentRegistration() {
		return currentRegistration;
	}

	public Student setCurrentRegistration(Registration currentRegistration) {
		this.currentRegistration = currentRegistration;
		return this;
	}

	public boolean isInsuranceConsent() {
		return insuranceConsent;
	}

	public Student setInsuranceConsent(boolean insuranceConsent) {
		this.insuranceConsent = insuranceConsent;
		return this;
	}

	public LocalDate getInsuranceConsentDate() {
		return insuranceConsentDate;
	}

	public Student setInsuranceConsentDate(LocalDate insuranceConsentDate) {
		this.insuranceConsentDate = insuranceConsentDate;
		return this;
	}

	public boolean isPhotoConsent() {
		return photoConsent;
	}

	public Student setPhotoConsent(boolean photoConsent) {
		this.photoConsent = photoConsent;
		return this;
	}

	public LocalDate getPhotoConsentDate() {
		return photoConsentDate;
	}

	public Student setPhotoConsentDate(LocalDate photoConsentDate) {
		this.photoConsentDate = photoConsentDate;
		return this;
	}

	public Address getAddress() {
		return address;
	}

	public Student setAddress(Address address) {
		this.address = address;
		return this;
	}

	public Student map(StudentEntity student) {
		this.id = student.getId();
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.dateOfBirth = student.getDateOfBirth();
		this.sex = student.getSex();
		this.insuranceInfo = new InsuranceInfo().map(student.getInsuranceInfo());
		this.insuranceConsent = student.isInsuranceConsent();
		this.insuranceConsentDate = student.getInsuranceConsentDate();
		this.photoConsent = student.isPhotoConsent();
		this.photoConsentDate = student.getInsuranceConsentDate();
		this.address = new Address().map(student.getAddress());
		
		if(this.parents != null && this.parents.size() > 0) {
			this.parents.clear();
		}
		if(student.getParents() != null) {
			for(ParentEntity parent: student.getParents()) {
				this.addParent(new Parent().map(parent));
			}
		}
		
		if(this.emergencyContacts != null && this.emergencyContacts.size() > 0 ) {
			this.emergencyContacts.clear();
		}
		if(student.getEmergencyContacts() != null) {
			for(EmergencyContactEntity emergencyContact : student.getEmergencyContacts()) {
				this.addEmergencyContact(new EmergencyContact().map(emergencyContact));
			}
		}
		
		return this;
	}
}
