package com.kavinunlimited.aathichudi.exception;

public class DBException extends AathichudiException {
	
	public DBException() {
		super(ExceptionCodes.DB_ERROR_CODE, ExceptionCodes.DB_ERROR_MESSAGE, ExceptionCodes.DB_ERROR_STATUS);
	}

}
