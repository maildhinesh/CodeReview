package com.kavinunlimited.aathichudi.dao.entity;

import com.kavinunlimited.aathichudi.domain.AddressEntity;

public class Address {
	
	private String streetName;
	
	private String city;
	
	private String state;
	
	private String zipcode;

	public String getStreetName() {
		return streetName;
	}

	public Address setStreetName(String streetName) {
		this.streetName = streetName;
		return this;
	}

	public String getCity() {
		return city;
	}

	public Address setCity(String city) {
		this.city = city;
		return this;
	}

	public String getState() {
		return state;
	}

	public Address setState(String state) {
		this.state = state;
		return this;
	}

	public String getZipcode() {
		return zipcode;
	}

	public Address setZipcode(String zipcode) {
		this.zipcode = zipcode;
		return this;
	}
	
	public Address map(AddressEntity entity) {
		this.streetName = entity.getStreetName();
		this.city = entity.getCity();
		this.state = entity.getState();
		this.zipcode = entity.getZipcode();
		return this;
	}

}
