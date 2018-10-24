package com.kavinunlimited.aathichudi.exception;

public class PinDoesNotMatchException extends AathichudiException {
	
	public PinDoesNotMatchException() {
		super(ExceptionCodes.PIN_DOES_NOT_MATCH_CODE, ExceptionCodes.PIN_DOES_NOT_MATCH_MESSAGE, ExceptionCodes.PIN_CODE_DOES_NOT_MATCH_STATUS);
	}

}
