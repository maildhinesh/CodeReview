package com.kavinunlimited.aathichudi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kavinunlimited.aathichudi.domain.response.ErrorResponse;
import com.kavinunlimited.aathichudi.exception.AathichudiException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(AathichudiException.class)
	public final ResponseEntity<ErrorResponse> handleAathichudiException(AathichudiException ex, WebRequest request) {
		ErrorResponse response = new ErrorResponse(ex);
		return new ResponseEntity<>(response, ex.getStatus());
	}

}
