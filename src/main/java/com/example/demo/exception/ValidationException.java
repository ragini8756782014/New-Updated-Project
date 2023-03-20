package com.example.demo.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String message;

	public ValidationException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
	
}