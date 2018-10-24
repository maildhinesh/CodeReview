package com.kavinunlimited.aathichudi.domain;

import com.kavinunlimited.aathichudi.dao.entity.Address;

public class AddressEntity {
	
	private String streetName;
	
	private String city;
	
	private String state;
	
	private String zipcode;

	public String getStreetName() {
		return streetName;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public AddressEntity setStreetName(String streetName) {
		this.streetName = streetName;
		return this;
	}

	public AddressEntity setCity(String city) {
		this.city = city;
		return this;
	}

	public AddressEntity setState(String state) {
		this.state = state;
		return this;
	}

	public AddressEntity setZipcode(String zipcode) {
		this.zipcode = zipcode;
		return this;
	}
	
	public AddressEntity map(Address address) {
		
		return this.setStreetName(address.getStreetName())
			.setCity(address.getCity())
			.setState(address.getState())
			.setZipcode(address.getZipcode());
	}

}
