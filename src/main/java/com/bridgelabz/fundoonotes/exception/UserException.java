package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;



/**
 * @author shaik shaiksha vali
 * 
 * */
public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus statusCode;
	private String statusMessage;
	public UserException(HttpStatus statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	
	
	

}