package com.example.demo.exception;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.response.BaseError;
import com.example.demo.response.ErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> validationExceptionHandler(ValidationException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
		BaseError baseError = new BaseError();
		baseError.setCode(exception.getCode());
		baseError.setMessage(exception.getMessage());
		errorResponse.setErrors(List.of(baseError));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

}


