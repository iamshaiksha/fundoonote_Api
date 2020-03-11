/**
 * 
 */
package com.bridgelabz.fundoonotes.dto;

/**
 * @author shaik shaiksha vali
 *
 */
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RemainderDto {

	public LocalDateTime getRemainder() {
		return remainder;
	}

	public void setRemainder(LocalDateTime remainder) {
		this.remainder = remainder;
	}

	private LocalDateTime remainder;

}