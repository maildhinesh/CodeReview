package com.kavinunlimited.aathichudi.exception;

public class RegistrationNotFoundException extends AathichudiException {
	
	public RegistrationNotFoundException() {
		super(ExceptionCodes.REGISTRATION_NOT_FOUND_CODE, ExceptionCodes.REGISTRATION_NOT_FOUND_MESSAGE, ExceptionCodes.REGISTRATION_NOT_FOUND_STATUS);
	}

}
