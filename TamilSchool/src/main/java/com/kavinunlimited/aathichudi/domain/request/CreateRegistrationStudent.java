package com.kavinunlimited.aathichudi.domain.request;

import javax.validation.constraints.NotNull;

import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;

public class CreateRegistrationStudent {
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private AathichudiGrade aathichudiGrade;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AathichudiGrade getAathichudiGrade() {
		return aathichudiGrade;
	}

	public void setAathichudiGrade(AathichudiGrade aathichudiGrade) {
		this.aathichudiGrade = aathichudiGrade;
	}
	
	

}
