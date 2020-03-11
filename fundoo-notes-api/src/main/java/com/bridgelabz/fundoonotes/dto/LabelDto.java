/**
 * 
 */
package com.bridgelabz.fundoonotes.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shaik shaiksha vali
 *
 */
@Data
@Getter
@Setter
/*Dto class for LabelData*/
public class LabelDto {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
