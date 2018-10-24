package com.kavinunlimited.aathichudi.exception;

import org.springframework.http.HttpStatus;

public class DatabaseException extends AathichudiException {

	public DatabaseException(String code, String message, HttpStatus status) {
		super(code, message, status);
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseException() {
		super(ExceptionCodes.DB_EXCEPTION_CODE, ExceptionCodes.DB_EXCEPTION_MESSAGE, ExceptionCodes.DB_ERROR_STATUS);
	}

}
