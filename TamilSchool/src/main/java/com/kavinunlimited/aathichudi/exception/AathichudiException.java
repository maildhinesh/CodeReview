package com.kavinunlimited.aathichudi.exception;

import org.springframework.http.HttpStatus;

public class AathichudiException extends RuntimeException {
	
	private String code;
	
	private String message;
	
	private HttpStatus status;

	public AathichudiException(String code, String message, HttpStatus status) {
		super();
		this.code = code;
		this.message = message;
		this.status = status;
	}

	public AathichudiException() {
		super();
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
