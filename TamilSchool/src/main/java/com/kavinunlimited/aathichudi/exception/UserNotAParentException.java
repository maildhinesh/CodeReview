package com.kavinunlimited.aathichudi.exception;

public class UserNotAParentException extends AathichudiException {
	
	public UserNotAParentException() {
		super(ExceptionCodes.USER_NOT_A_PARENT_CODE, ExceptionCodes.USER_NOT_A_PARENT_MESSAGE, ExceptionCodes.USER_NOT_A_PARENT_STATUS);
	}

}
