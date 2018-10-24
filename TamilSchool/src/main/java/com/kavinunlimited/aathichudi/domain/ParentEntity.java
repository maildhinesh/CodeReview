package com.kavinunlimited.aathichudi.domain;

import com.kavinunlimited.aathichudi.dao.entity.Parent;

public class ParentEntity {
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private boolean isPrimary;
	
	private String homePhone;
	
	private String cellPhone;
	
	private AddressEntity address;

	public String getId() {
		return id;
	}

	public ParentEntity setId(String id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public ParentEntity setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public ParentEntity setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public ParentEntity setEmail(String email) {
		this.email = email;
		return this;
	}

	public ParentEntity setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
		return this;
	}

	public ParentEntity setHomePhone(String homePhone) {
		this.homePhone = homePhone;
		return this;
	}

	public ParentEntity setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
		return this;
	}

	public ParentEntity setAddress(AddressEntity address) {
		this.address = address;
		return this;
	}
		
	public ParentEntity map(Parent parent) {
		
		return this.setFirstName(parent.getFirstName())
					.setLastName(parent.getLastName())
					.setId(parent.getId())
					.setEmail(parent.getEmail())
					.setCellPhone(parent.getCellPhoneNumber())
					.setHomePhone(parent.getHomePhoneNumber())
					.setAddress((parent.getAddress() != null) ? new AddressEntity().map(parent.getAddress()) : null)
					.setPrimary(parent.isPrimary());
	}
}
