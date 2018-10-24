package com.kavinunlimited.aathichudi.domain.request;

import javax.validation.constraints.NotNull;

public class CreateRegistrationRequest {
	
	@NotNull
	private String email;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private CreateRegistrationStudent student;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public CreateRegistrationStudent getStudent() {
		return student;
	}

	public void setStudent(CreateRegistrationStudent student) {
		this.student = student;
	}

}