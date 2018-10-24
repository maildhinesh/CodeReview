package com.kavinunlimited.aathichudi.dao.entity;

import com.kavinunlimited.aathichudi.domain.InsuranceInfoEntity;

public class InsuranceInfo {
	
	
	private String insuranceProvider;
	
	private String insuranceId;
	
	private String doctor;
	
	private String doctorContact;

	public String getInsuranceProvider() {
		return insuranceProvider;
	}

	public InsuranceInfo setInsuranceProvider(String insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
		return this;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public InsuranceInfo setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
		return this;
	}

	public String getDoctor() {
		return doctor;
	}

	public InsuranceInfo setDoctor(String doctor) {
		this.doctor = doctor;
		return this;
	}

	public String getDoctorContact() {
		return doctorContact;
	}

	public InsuranceInfo setDoctorContact(String doctorContact) {
		this.doctorContact = doctorContact;
		return this;
	}
	
	public InsuranceInfo map(InsuranceInfoEntity entity) {
		this.doctor = entity.getDoctor();
		this.doctorContact = entity.getDoctorContact();
		this.insuranceProvider = entity.getInsuranceProvider();
		this.insuranceId = entity.getInsuranceId();
		return this;
	}

}
