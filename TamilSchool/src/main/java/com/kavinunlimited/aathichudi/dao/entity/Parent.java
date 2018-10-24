package com.kavinunlimited.aathichudi.dao.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kavinunlimited.aathichudi.domain.AddressEntity;
import com.kavinunlimited.aathichudi.domain.ParentEntity;

public class Parent {
	
	@Id
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String homePhoneNumber;
	
	private String cellPhoneNumber;
	
	private String email;
	
	private boolean primary;
	
	@DBRef(lazy = true)
	@JsonManagedReference
	List<Student> students;
	
	private Address address;

	public String getId() {
		return id;
	}

	public Parent setId(String id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Parent setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Parent setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public Parent setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
		return this;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public Parent setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
		return this;
	}

	public List<Student> getStudents() {
		return students;
	}

	public Parent setStudents(List<Student> students) {
		this.students = students;
		return this;
	}
	
	public Parent addStudent(Student student) {
		if(this.students == null) {
			this.students = new ArrayList<Student>();
		}
		
		this.students.add(student);
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Parent setEmail(String email) {
		this.email = email;
		return this;
	}

	public boolean isPrimary() {
		return primary;
	}

	public Parent setPrimary(boolean prime) {
		this.primary = prime;
		return this;
	}

	public Address getAddress() {
		return address;
	}

	public Parent setAddress(Address address) {
		this.address = address;
		return this;
	}
	
	public Parent map(ParentEntity parent) {
		return this.setFirstName(parent.getFirstName())
				.setId(parent.getId())
				.setLastName(parent.getLastName())
				.setEmail(parent.getEmail())
				.setCellPhoneNumber(parent.getCellPhone())
				.setHomePhoneNumber(parent.getHomePhone())
				.setAddress((parent.getAddress() != null) ? new Address().map(parent.getAddress()) : null)
				.setPrimary(parent.isPrimary());
	}
	
}
