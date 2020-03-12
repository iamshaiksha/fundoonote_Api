
package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNoteDto;
import com.bridgelabz.fundoonotes.model.NoteInformation;

/**
 * @author shaik shaiksha vali
 *
 */
public interface NoteService {


NoteInformation create(UserNoteDto userNoteDto, String token);

List<NoteInformation> getAllNotes();

NoteInformation getNote(String id);

List<NoteInformation> updateNote(UpdateNote updateNoteDto, String token);

void deleteNote(Long id, String token);

void addColour(Long noteId, String colour);

List<NoteInformation> getNoteByUserId(String token);

NoteInformation pinned(Long noteId, String token);

NoteInformation archieveNote(Long noteId, String token);

NoteInformation addReminder(Long noteId, String token, RemainderDto remainder);

NoteInformation removeRemainder(Long noteId, String token);

List<NoteInformation> getarchieved(String token);

List<NoteInformation> getAllPinneded(String token);

List<String> ascSortByName();

List<String> descsortByName();
	
	
}
