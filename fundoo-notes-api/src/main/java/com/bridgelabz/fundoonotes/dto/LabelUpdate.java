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
/*Dto class for updating LabelData*/
@Data
public class LabelUpdate {

	private Long labelId;
	private String name;
	public Long getLabelId() {
		return labelId;
	}
	public String getName() {
		return name;
	}
	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
