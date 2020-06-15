package com.bridgelabz.fundoonotes.controller;
/**
 * @author shaik shaiksha vali
 *
 */
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.Delete;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNoteDto;
import com.bridgelabz.fundoonotes.model.LabelInformation;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.service.UserServiceSearch;
@RestController
@PropertySource("classpath:Message.properties")
@RequestMapping("/notes")
public class NoteController {
	@Autowired
	private NoteService noteService;
	@Autowired
	private Environment environment;
	@Autowired
	private UserServiceSearch userServiceSearch;
	/**
	 * 
	 * @param userNoteDto
	 * @param token
	 * @return noteInformation
	 */
	/*Api for creating a note*/
	@PostMapping("/create")
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
	@GetMapping("/getAllNotes")
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
	@GetMapping("/labelnote/{noteId}")
	public ResponseEntity<UserResponse> getNote(@PathVariable long noteId)
	{

		NoteInformation note=noteService.getNote(noteId);
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
	@PutMapping("/update/{token}")
	public ResponseEntity<UserResponse> updateNote(@PathVariable("token") String token,@RequestBody UpdateNote updateNoteDto)
	{
		
		System.out.println("====update enter");
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
	@PutMapping("/delete")
	public ResponseEntity<UserResponse> deletNote(@RequestParam Long noteId,@RequestHeader String token )
	{
		System.out.println("####");
		System.out.println("nid="+noteId);
		NoteInformation message= noteService.deleteNote(token, noteId);
		System.out.println("==="+message);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new UserResponse(environment.getProperty("203"),200,message));
	}
	/**
	 * 
	 * @param noteId
	 * @param colour
	 * 
	 */
	/*Api for  delete*/
	@PutMapping("/Color")
	public ResponseEntity<UserResponse> addColour(@RequestParam Long noteId, @RequestHeader String token,
			@RequestBody String color)
	{
		System.out.println("color="+color);
		noteService.addColour(noteId,color);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(environment.getProperty("200"), 200));
	}
	/**
	 * 
	 * @param token
	 * @return List<NoteInformation>
	 */
	@GetMapping(value = "/users/{token}")
	public List<NoteInformation> getNotesByUserId(@PathVariable String token) {
		List<NoteInformation> result = noteService.getNoteByUserId(token);
		System.out.println("-----------result"+result);
		if (result != null) {
			 return result;
//			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "sucess")
//					.body(new UserResponse(environment.getProperty("202"),202, result));
		}
		return null;
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new UserResponse(environment.getProperty("400"), 400,result));
	}
	/**
	 * 
	 * @param noteId
	 * @param token
	 * @return NoteInformation
	 */
	@PutMapping("/isPin")
	public ResponseEntity<UserResponse> pinned(@RequestParam Long noteId,@RequestHeader String token) {
		NoteInformation noteInformation= noteService.pinned(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse("note pinned",200,noteInformation));
	}
	/**
	 * 
	 * @param noteId
	 * @param token
	 * @return NoteInformation
	 */
	@PutMapping("/isunPin")
	public ResponseEntity<UserResponse> unpinned(@RequestParam Long noteId,@RequestHeader String token) {
		NoteInformation noteInformation= noteService.unpinned(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse("note pinned",200,noteInformation));
	}
	/**
	 * 
	 * @param token
	 * @return List<NoteInformation>
	 */
	@GetMapping("/getAllPinned/users/{token}")
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
	@PutMapping("/isArchieve/{token}")
	public ResponseEntity<UserResponse> archieved(@RequestBody NoteDto noteId, @PathVariable String token) {
		System.out.println("noteInfo");
		NoteInformation noteInformation=noteService.archieveNote( token,noteId.getNid());
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse("note archieved",200,noteInformation));
	}
	/**
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/getAllArchieve/users/{token}")
	public ResponseEntity<UserResponse> getArchieve(@PathVariable String token) {
		List<NoteInformation> note = noteService.getarchieved(token);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse("archieved notes",200,note));
	}
	/**
	 * 
	 * @param token
	 * @param noteId
	 * @param remainderDto
	 * @return
	 */
	@PostMapping("/addremainder/{token}")
	public ResponseEntity<UserResponse> addRemainder(@PathVariable String token,
			@RequestBody RemainderDto remainderDto) {
		NoteInformation notes = noteService.addReminder(remainderDto.getNoteId(), token, remainderDto);
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
	@DeleteMapping("/removeRemainder")
	public ResponseEntity<UserResponse> removeRemainder(@RequestHeader String token, @RequestParam Long noteId) {
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


	@GetMapping(value = "/ascendingSortByTitle")
	public ResponseEntity<UserResponse> SortByNoteTitle() {
		List<String> result = noteService.ascSortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse("Notes Sorted sucessfully",201,result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse("Note Not Exist", 400,result));
	}

	@GetMapping(value = "/descSortByTitle")
	public ResponseEntity<UserResponse> descSortByNoteTitle() {
		List<String> result = noteService.descsortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse("Sorted sucessfully",201,result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse("Note Not Exist",400,result));
	}
	
	/*
	 * API for getting all trashed Notes
	 */
	@GetMapping("/trash/{token}")
	public List<NoteInformation> getTrashed(@PathVariable String token) {
		List<NoteInformation> note = noteService.getAlltrashednotes(token);
		return note;
	}
	
	
	@GetMapping("/search")
	public List<NoteInformation> searchTitle(@RequestParam String title) {
		List<NoteInformation> data =null;
		try {
			data = userServiceSearch.getNoteByTitleAndDescription(title);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return data;
	}
	
}

