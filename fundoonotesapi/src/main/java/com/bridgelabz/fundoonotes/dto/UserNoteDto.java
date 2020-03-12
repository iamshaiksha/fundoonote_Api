package com.bridgelabz.fundoonotes.dto;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
/*Dto class for User note data*/
public class UserNoteDto {
	@NotNull
	private String title;
	@NotNull
	private String description;
	
	
	
	
	
}
