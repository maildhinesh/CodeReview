package com.kavinunlimited.aathichudi.exception;

public class InvalidRegistrationException extends AathichudiException {
	
	public InvalidRegistrationException() {
		super(ExceptionCodes.REGISTARTION_INVALID_CODE, ExceptionCodes.REGISTRATION_INVALID_MESSAGE, ExceptionCodes.REGISTRATION_INVALID_STATUS);
	}

}
