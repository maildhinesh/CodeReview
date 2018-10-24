package com.kavinunlimited.aathichudi.exception;

public class UserAlreadyExistsException extends AathichudiException {
	
	public UserAlreadyExistsException() {
		super(ExceptionCodes.USER_ALREADY_EXISTS_CODE, ExceptionCodes.USER_ALREADY_EXISTS_MESSAGE, ExceptionCodes.USER_ALREADY_EXISTS_STATUS);
	}
}
