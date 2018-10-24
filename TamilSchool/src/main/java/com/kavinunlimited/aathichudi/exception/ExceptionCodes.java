package com.kavinunlimited.aathichudi.exception;

import org.springframework.http.HttpStatus;

public class ExceptionCodes {
	
	public static final String DB_EXCEPTION_CODE = "500.001";
	public static final String DB_EXCEPTION_MESSAGE = "Error writing / reading into database";
	public static final HttpStatus DB_EXCEPTION_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
	
	public static final String USER_ALREADY_EXISTS_CODE = "400.002";
	public static final String USER_ALREADY_EXISTS_MESSAGE = "Parent account already exists. Ask parent to login to his/her account";
	public static final HttpStatus USER_ALREADY_EXISTS_STATUS = HttpStatus.CONFLICT;
	
	public static final String REGISTRATION_ALREADY_EXISTS_CODE = "400.003";
	public static final String REGISTRATION_ALREADY_EXISTS_MESSAGE = "Registration already exists. Duplicate Registration";
	public static final HttpStatus REGISTRATION_ALREADY_EXISTS_STATUS = HttpStatus.CONFLICT;
	
	public static final String REGISTRATION_NOT_FOUND_CODE = "404.001";
	public static final String REGISTRATION_NOT_FOUND_MESSAGE = "Registration does not exist";
	public static final HttpStatus REGISTRATION_NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;
	
	public static final String USER_NOT_FOUND_CODE = "404.002";
	public static final String USER_NOT_FOUND_MESSAGE = "User not found";
	public static final HttpStatus USER_NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;
	
	public static final String REGISTRATION_COMPLETE_CODE = "400.004";
	public static final String REGISTRATION_COMPLETE_MESSAGE = "Registration already completed";
	public static final HttpStatus REGISTRATION_COMPLETE_STATUS = HttpStatus.BAD_REQUEST;
	
	public static final String REGISTARTION_INVALID_CODE = "400.008";
	public static final String REGISTRATION_INVALID_MESSAGE = "Registration not valid, or parent is not assigned to this registration";
	public static final HttpStatus REGISTRATION_INVALID_STATUS = HttpStatus.BAD_REQUEST;
	
	
	public static final String DUPLICATE_USER_CODE = "400.005";
	public static final String DUPLICATE_USER_MESSAGE = "User already exists";
	public static final HttpStatus DUPLICATE_USER_STATUS = HttpStatus.CONFLICT;
	
	public static final String TOKEN_EXPIRED_CODE = "400.006";
	public static final String TOKEN_EXPIRED_MESSAGE = "Token Invalid";
	public static final HttpStatus TOKEN_EXPIRED_STATUS = HttpStatus.FORBIDDEN;
	
	public static final String USER_NOT_A_PARENT_CODE = "400.007";
	public static final String USER_NOT_A_PARENT_MESSAGE = "User is not a parent";
	public static final HttpStatus USER_NOT_A_PARENT_STATUS = HttpStatus.BAD_REQUEST;
	
	public static final String PIN_DOES_NOT_MATCH_CODE = "404.003";
	public static final String PIN_DOES_NOT_MATCH_MESSAGE = "Pin does not match";
	public static final HttpStatus PIN_CODE_DOES_NOT_MATCH_STATUS = HttpStatus.NOT_FOUND;
	
	public static final String DB_ERROR_CODE = "500.001";
	public static final String DB_ERROR_MESSAGE = "Cannot read/write to database";
	public static final HttpStatus DB_ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
}
