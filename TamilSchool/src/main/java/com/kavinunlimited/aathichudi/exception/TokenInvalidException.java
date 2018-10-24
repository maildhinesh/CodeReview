package com.kavinunlimited.aathichudi.exception;

public class TokenInvalidException extends AathichudiException {

	public TokenInvalidException() {
		super(ExceptionCodes.TOKEN_EXPIRED_CODE, ExceptionCodes.TOKEN_EXPIRED_MESSAGE, ExceptionCodes.TOKEN_EXPIRED_STATUS);
	}
}
