/**
 * 
 */
package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;


/**
 * @author shaik shaiksha vali
 *
 */
public class NoteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private HttpStatus statusCode;
	private String statusMessage;
	
	public  NoteException(HttpStatus statusCode, String message) {
		super(message);
		this.statusCode=statusCode;
		this.statusMessage=message;
	}
		
}
