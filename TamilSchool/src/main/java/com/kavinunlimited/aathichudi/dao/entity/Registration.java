package com.kavinunlimited.aathichudi.dao.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kavinunlimited.aathichudi.dao.entity.enums.AathichudiGrade;
import com.kavinunlimited.aathichudi.dao.entity.enums.RegistrationStatus;
import com.kavinunlimited.aathichudi.dao.entity.enums.SchoolGrade;
import com.kavinunlimited.aathichudi.domain.RegistrationEntity;

public class Registration {
	
	@Id
	private String id;
	
	private LocalDate registrationDate;
	
	@DBRef(lazy = true)
	@JsonManagedReference
	private Student student;
	
	private SchoolGrade schoolGrade;
	
	private AathichudiGrade aathichudiGrade;
	
	private RegistrationStatus status;

	public String getId() {
		return id;
	}

	public Registration setId(String id) {
		this.id = id;
		return this;
	}

	public SchoolGrade getSchoolGrade() {
		return schoolGrade;
	}

	public Registration setSchoolGrade(SchoolGrade schoolGrade) {
		this.schoolGrade = schoolGrade;
		return this;
	}

	public AathichudiGrade getAathichudiGrade() {
		return aathichudiGrade;
	}

	public Registration setAathichudiGrade(AathichudiGrade aathichudiGrade) {
		this.aathichudiGrade = aathichudiGrade;
		return this;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public Registration setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
		return this;
	}

	public Student getStudent() {
		return student;
	}

	public Registration setStudent(Student student) {
		this.student = student;
		return this;
	}

	public RegistrationStatus getStatus() {
		return status;
	}
	
	public Registration moveNextStatus() {
		if(this.status == RegistrationStatus.PENDING_USER) {
			this.status = RegistrationStatus.PENDING_ADMIN;
		} else if(this.status == RegistrationStatus.PENDING_ADMIN) {
			this.status = RegistrationStatus.COMPLETE;
		}
		return this;
	}

	public Registration setStatus(RegistrationStatus status) {
		this.status = status;
		return this;
	}
	
	public Registration map(RegistrationEntity entity) {
		this.id = entity.getId();
		this.aathichudiGrade = entity.getAathichudiGrade();
		this.registrationDate = entity.getRegistrationDate();
		this.schoolGrade = entity.getSchoolGrade();
		this.status = entity.getStatus();
		this.student = new Student().map(entity.getStudent());
		return this;
	}
	
}
