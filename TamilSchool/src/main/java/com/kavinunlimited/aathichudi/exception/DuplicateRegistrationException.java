package com.kavinunlimited.aathichudi.exception;

public class DuplicateRegistrationException extends AathichudiException {
	
	public DuplicateRegistrationException() {
		super(ExceptionCodes.REGISTRATION_ALREADY_EXISTS_CODE, ExceptionCodes.REGISTRATION_ALREADY_EXISTS_MESSAGE, ExceptionCodes.REGISTRATION_ALREADY_EXISTS_STATUS);
	}
}
