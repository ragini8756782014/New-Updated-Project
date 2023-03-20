package com.example.demo.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseError implements Serializable {

	private static final long serialVersionUID = 1L;

    private String code;

	private String message;

	
	
}