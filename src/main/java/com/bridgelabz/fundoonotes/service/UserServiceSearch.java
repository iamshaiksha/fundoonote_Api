package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;

import com.bridgelabz.fundoonotes.model.NoteInformation;

public interface UserServiceSearch {
	public String createNote(NoteInformation note) throws IOException ;
	public NoteInformation findById(String id) throws Exception;

	public String upDateNote(NoteInformation note) throws Exception;

	public String deleteNote(String id) throws IOException;

//	List<Noteinfo> searchByTitle(String title, String token) throws IOException;
//
//	List<Noteinfo> searchByTechnology(String technology) throws Exception;
	 List<NoteInformation> getNoteByTitleAndDescription(String text);
	
	
}
