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
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
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
