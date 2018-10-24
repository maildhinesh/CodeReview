package com.kavinunlimited.aathichudi.utilities.cache;

import java.time.LocalDate;

public class Token {
	
	private String token;
	
	private String pin;

	private String email;
	
	private LocalDate expiryDate;
	
	private boolean pinMatched;
	
	private int noOfFailedAttempts = 0;

	public String getToken() {
		return token;
	}

	public Token setToken(String token) {
		this.token = token;
		return this;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public Token setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Token setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public String getPin() {
		return pin;
	}

	public Token setPin(String pin) {
		this.pin = pin;
		return this;
	}

	public boolean isPinMatched() {
		return pinMatched;
	}

	public Token setPinMatched(boolean pinMatched) {
		this.pinMatched = pinMatched;
		return this;
	}

	public int getNoOfFailedAttempts() {
		return noOfFailedAttempts;
	}

	public Token setNoOfFailedAttempts(int noOfFailedAttempts) {
		this.noOfFailedAttempts = noOfFailedAttempts;
		return this;
	}

}
