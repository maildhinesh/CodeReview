package com.kavinunlimited.aathichudi.exception;

public class UserNotFoundException extends AathichudiException {
	
	public UserNotFoundException() {
		super(ExceptionCodes.USER_NOT_FOUND_CODE, ExceptionCodes.USER_NOT_FOUND_MESSAGE, ExceptionCodes.USER_NOT_FOUND_STATUS);
	}

}
