package com.example.demo.response;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int status;

	private String message;

	private List<BaseError> errors;
}
