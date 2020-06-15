package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

public class UpdateNote {
	@NotBlank
	private Long noteId; 
	@NotNull
	private String title;
	@NotNull
	private String description;
	
	
	public Long getNoteId() {
		return noteId;
	}
	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
