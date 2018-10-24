package com.kavinunlimited.aathichudi.domain.response;

import com.kavinunlimited.aathichudi.exception.AathichudiException;

public class ErrorResponse extends TamilSchoolResponse {
	
	private String code;
	
	private String message;
	
	public ErrorResponse(AathichudiException e) {
		this.code = e.getCode();
		this.message = e.getMessage();
		this.setSuccess(false);
	}
	
	public ErrorResponse() { }

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

}
