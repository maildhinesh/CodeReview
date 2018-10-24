package com.kavinunlimited.aathichudi.domain;

import com.kavinunlimited.aathichudi.dao.entity.InsuranceInfo;

public class InsuranceInfoEntity {

	private String insuranceProvider;
	
	private String insuranceId;
	
	private String doctor;
	
	private String doctorContact;

	public String getInsuranceProvider() {
		return insuranceProvider;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public String getDoctor() {
		return doctor;
	}

	public String getDoctorContact() {
		return doctorContact;
	}

	public InsuranceInfoEntity setInsuranceProvider(String insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
		return this;
	}

	public InsuranceInfoEntity setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
		return this;
	}

	public InsuranceInfoEntity setDoctor(String doctor) {
		this.doctor = doctor;
		return this;
	}

	public InsuranceInfoEntity setDoctorContact(String doctorContact) {
		this.doctorContact = doctorContact;
		return this;
	}
	
	public InsuranceInfoEntity map(InsuranceInfo insuranceInfo) {
		return this.setInsuranceProvider(insuranceInfo.getInsuranceProvider())
					.setInsuranceId(insuranceInfo.getInsuranceId())
					.setDoctor(insuranceInfo.getDoctor())
					.setDoctorContact(insuranceInfo.getDoctorContact());
	}

}
