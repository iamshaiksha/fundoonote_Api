
package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNoteDto;
import com.bridgelabz.fundoonotes.model.LabelInformation;
import com.bridgelabz.fundoonotes.model.NoteInformation;

/**
 * @author shaik shaiksha vali
 *
 */
public interface NoteService {

/**
 * 
 * @param userNoteDto
 * @param token
 * @return NoteInformation
 */
NoteInformation create(UserNoteDto userNoteDto, String token);
/**
 * 
 * @return List<NoteInformation
 */
List<NoteInformation> getAllNotes();
/**
 * 
 * @param id
 * @return NoteInformation  
 */
NoteInformation getNote(String id);
/**
 * 
 * @param updateNoteDto
 * @param token
 * @return List<NoteInformation>
 */
List<NoteInformation> updateNote(UpdateNote updateNoteDto, String token);
/**
 * 
 * @param id
 * @param token
 */
void deleteNote(Long id, String token);
/**
 * 
 * @param noteId
 * @param colour
 * @return 
 */
String addColour(Long noteId, String colour);
/**
 * 
 * @param token
 * @return List<NoteInformation>
 */
List<NoteInformation> getNoteByUserId(String token);

/**
 * 
 * @param noteId
 * @param token
 * @return NoteInformation
 */
NoteInformation pinned(Long noteId, String token);
/**
 * 
 * @param noteId
 * @param token
 * @return NoteInformation
 */
NoteInformation archieveNote(Long noteId, String token);
/**
 * 
 * @param noteId
 * @param token
 * @param remainder
 * @return NoteInformation
 */
NoteInformation addReminder(Long noteId, String token, RemainderDto remainder);
/**
 * 
 * @param noteId
 * @param token
 * @return NoteInformation
 */
NoteInformation removeRemainder(Long noteId, String token);
/**
 * 
 * @param token
 * @return List<NoteInformation>
 */
List<NoteInformation> getarchieved(String token);
/**
 * 
 * @param token
 * @return List<NoteInformation
 */
List<NoteInformation> getAllPinneded(String token);
/**
 * 
 * @return List<String>
 */
List<String> ascSortByName();
/**
 * 
 * @return List<String>
 */
List<String> descsortByName();
/**
 * 
 * @param noteId
 * @param token
 * @return List<LabelInformation>
 */
List<LabelInformation> getLabelsFromNote(Long noteId, String token);
	
	
}
