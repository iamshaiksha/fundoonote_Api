/**
 * 
 */
package com.bridgelabz.fundoonotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shaik shaiksha vali
 *
 */
/*Dto class for updating LabelData*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LabelUpdate {

	private Long labelId;
	private String name;
	
	
}
