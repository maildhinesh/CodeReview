package com.kavinunlimited.aathichudi.domain;

import java.time.LocalDate;

import com.kavinunlimited.aathichudi.dao.entity.Registration;
import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;
import com.kavinunlimited.aathichudi.dao.entity.enums.RegistrationStatus;
import com.kavinunlimited.aathichudi.dao.entity.enums.SchoolGrade;

public class RegistrationEntity {
	
	private String id;
	
	private LocalDate registrationDate;

	private SchoolGrade schoolGrade;
	
	private AathichudiGrade aathichudiGrade;
	
	private RegistrationStatus status;
	
	private StudentEntity student;
	
	private boolean userCreated;

	public String getId() {
		return id;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public SchoolGrade getSchoolGrade() {
		return schoolGrade;
	}

	public AathichudiGrade getAathichudiGrade() {
		return aathichudiGrade;
	}

	public RegistrationStatus getStatus() {
		return status;
	}

	public RegistrationEntity setId(String id) {
		this.id = id;
		return this;
	}

	public RegistrationEntity setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
		return this;
	}

	public RegistrationEntity setSchoolGrade(SchoolGrade schoolGrade) {
		this.schoolGrade = schoolGrade;
		return this;
	}

	public RegistrationEntity setAathichudiGrade(AathichudiGrade aathichudiGrade) {
		this.aathichudiGrade = aathichudiGrade;
		return this;
	}

	public RegistrationEntity setStatus(RegistrationStatus status) {
		this.status = status;
		return this;
	}
	
	public StudentEntity getStudent() {
		return this.student;
	}
	
	public RegistrationEntity setStudent(StudentEntity student) {
		this.student = student;
		return this;
	}
	
	public RegistrationEntity map(Registration registration) {
		return 
		this.setId(registration.getId())
			.setAathichudiGrade(registration.getAathichudiGrade())
			.setSchoolGrade(registration.getSchoolGrade() )
			.setRegistrationDate(registration.getRegistrationDate())
			.setStatus(registration.getStatus() )
			.setStudent(new StudentEntity().map(registration.getStudent()));
	}

	public boolean isUserCreated() {
		return userCreated;
	}

	public RegistrationEntity setUserCreated(boolean userCreated) {
		this.userCreated = userCreated;
		return this;
	}
	
	

}
