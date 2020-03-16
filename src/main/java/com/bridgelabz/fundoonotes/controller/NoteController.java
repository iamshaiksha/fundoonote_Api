package com.bridgelabz.fundoonotes.controller;
/**
 * @author shaik shaiksha vali
 *
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNoteDto;
import com.bridgelabz.fundoonotes.model.LabelInformation;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.NoteService;
@RestController
@PropertySource("classpath:Message.properties")
public class NoteController {
	@Autowired
	private NoteService noteService;
	@Autowired
	private Environment environment;
	/**
	 * 
	 * @param userNoteDto
	 * @param token
	 * @return noteInformation
	 */
	/*Api for creating a note*/
	@PostMapping("/note/create")
	public ResponseEntity<UserResponse> create(@RequestBody UserNoteDto userNoteDto,@RequestHeader("token") String token)
	{

		NoteInformation noteInformation=noteService.create(userNoteDto,token);

		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(environment.getProperty("201"),201,noteInformation));

	}
	/**
	 * 
	 * @return List<NoteInformation>
	 */
	/*Api for fetching all notes*/
	@GetMapping("note/getAllNotes")
	public List<NoteInformation> getAllnotes()
	{


		return noteService.getAllNotes();

	}
	/**
	 * 
	 * @param id
	 * @return NoteInformation
	 */
	/*Api for fetching note by using id*/
	@GetMapping("note/{id}")
	public ResponseEntity<UserResponse> getNote(@PathVariable String id)
	{

		NoteInformation note=noteService.getNote(id);
		if(note!=null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"),202,note));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(environment.getProperty("400"),400,note));
	}
	/**
	 * 
	 * @param updateNoteDto
	 * @param token
	 * @return List<NoteInformation>
	 */
	/*Api for  update*/
	@PutMapping("note/update")
	public ResponseEntity<UserResponse> updateNote(@RequestBody UpdateNote updateNoteDto,@RequestHeader("token") String token)
	{


		List<NoteInformation> noteInformation=noteService.updateNote(updateNoteDto,token);
		if(noteInformation!=null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"),202, updateNoteDto));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(environment.getProperty("400"),400, updateNoteDto));
		
	}
	/**
	 * 
	 * @param id
	 * @param token
	 *
	 */
	/*Api for  delete*/
	@DeleteMapping("note/delete/{id}")
	public ResponseEntity<UserResponse> deleteNote(@PathVariable Long id,@RequestHeader("token") String token)
	{
		noteService.deleteNote(id,token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(environment.getProperty("201"), 200));
	}
	/**
	 * 
	 * @param noteId
	 * @param colour
	 * 
	 */
	/*Api for  delete*/
	@PostMapping("note/addColour")
	public ResponseEntity<UserResponse> addColour(@RequestParam("noteId") Long noteId,@RequestParam("colour") String colour)
	{
		noteService.addColour(noteId,colour);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(environment.getProperty("200"), 200));
	}
	/**
	 * 
	 * @param token
	 * @return List<NoteInformation>
	 */
	@GetMapping(value = "/notes/users/{token}")
	public ResponseEntity<UserResponse> getNotesByUserId(@PathVariable String token) {
		List<NoteInformation> result = noteService.getNoteByUserId(token);
		System.out.println("-----------result"+result);
		if (result != null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "sucess")
					.body(new UserResponse(environment.getProperty("202"),202, result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new UserResponse(environment.getProperty("400"), 400,result));
	}
	/**
	 * 
	 * @param noteId
	 * @param token
	 * @return NoteInformation
	 */
	@PutMapping("/notes/pin/{noteId}/users/{token}")
	public ResponseEntity<UserResponse> pinned(@PathVariable Long noteId, @PathVariable String token) {
		  NoteInformation noteInformation= noteService.pinned(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse("note pinned",200,noteInformation));
	}
	/**
	 * 
	 * @param token
	 * @return List<NoteInformation>
	 */
	@GetMapping("/notes/getAllPinned/users/{token}")
	public ResponseEntity<UserResponse> getPinned(@PathVariable String token) {
	        List<NoteInformation> note = noteService.getAllPinneded(token);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(" pinned notes",200,note));
	}
	/**
	 * 
	 * @param noteId
	 * @param token
	 * @return NoteInformation
	 */
	/* API for archieve a Note */
	@PutMapping("/note/archieve/{noteId}/users/{token}")
	public ResponseEntity<UserResponse> archieved(@PathVariable Long noteId, @PathVariable String token) {
		    NoteInformation noteInformation=noteService.archieveNote(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse("note archieved",200,noteInformation));
	}
	/**
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/notes/getAllArchieve/users/{token}")
	public ResponseEntity<UserResponse> getArchieve(@PathVariable String token) {
		  List<NoteInformation> note = noteService.getarchieved(token);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(" archieved notes",200,note));
	}
	/**
	 * 
	 * @param token
	 * @param noteId
	 * @param remainderDto
	 * @return
	 */
	@PostMapping("/notes/addremainder/{noteId}/users/{token}")
	public ResponseEntity<UserResponse> addRemainder(@PathVariable String token, @PathVariable Long noteId,
			@RequestBody RemainderDto remainderDto) {
		     NoteInformation notes = noteService.addReminder(noteId, token, remainderDto);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(" reminder Added to the notes",200,notes));
	}
	/**
	 * 
	 * @param token
	 * @param noteId
	 * @return NoteInformation
	 */
	/*
	 * API for removing remainder Notes
	 */
	@DeleteMapping("/notes/removeRemainder/{noteId}/users/{token}")
	public ResponseEntity<UserResponse> removeRemainder(@PathVariable String token, @PathVariable Long noteId) {
		     NoteInformation noteInformation= noteService.removeRemainder(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse("Reminder notes Removed",200,noteInformation));
	}
	/**
	 * 
	 * @param noteId
	 * @param token
	 * @return List<LabelInformation>
	 */
	@GetMapping("/getlabel")
	public List<LabelInformation> getLabel(@RequestParam Long noteId, @RequestHeader String token) {
		List<LabelInformation> listLabel = noteService.getLabelsFromNote(noteId, token);
		return listLabel;
	}	
	
	
	@GetMapping(value = "/notes/ascendingSortByTitle")
	public ResponseEntity<UserResponse> SortByNoteTitle() {
		List<String> result = noteService.ascSortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse("Notes Sorted sucessfully",201,result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse("Note Not Exist", 400,result));
	}
	
	@GetMapping(value = "/notes/descSortByTitle")
	public ResponseEntity<UserResponse> descSortByNoteTitle() {
		List<String> result = noteService.descsortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse("Sorted sucessfully",201,result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse("Note Not Exist",400,result));
	}
}
