/**
 * 
 */
package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;


/**
 * @author shaik shaiksha vali
 *
 */
public class LabelException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus statusCode;
	private String statusMessage;
	public LabelException(HttpStatus statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

}
