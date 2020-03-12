/**
 * 
 */
package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shaik shaiksha vali
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
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