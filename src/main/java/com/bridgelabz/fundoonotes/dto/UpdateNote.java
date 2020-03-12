package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateNote {
	@NotBlank
	private long id; 
	@NotNull
	private String title;
	@NotNull
	private String description;
	private int isArchieved;
	private int isPinned;
	private int isTrashed;
	private LocalDateTime createDateAndTime;
	private LocalDateTime upDateTime;
	
	
}
