/**
 * 
 */
package com.bridgelabz.fundoonotes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.ServiceCollaborator;

/**
 * @author shaik shaiksha vali
 *
 */
@RestController
@PropertySource("classpath:Message.properties")
public class Collaborator {
	@Autowired
	private ServiceCollaborator serviceCollborator;
	@Autowired
	private Environment environment;
	/**
	 * 
	 * @param noteId
	 * @param email
	 * @param token
	 * @return noteInformation
	 */
	//Api for adding collaborator
	@PostMapping("addcollaborator")
	public ResponseEntity<UserResponse> addCollaborator(@RequestParam("noteId") Long noteId,@RequestParam("email") String email,@RequestHeader("token") String token)
	{
		Optional<NoteInformation> noteInformation=serviceCollborator.addCollaborator(noteId,email,token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(environment.getProperty("201"),201,noteInformation));
		
	}
	/**
	 * 
	 * @param token
	 * @return 
	 * @return List<NoteInformation>
	 */
	//Api for fetching All collaborators
	@GetMapping("/getAllcollaborators")
	public  List<NoteInformation> getAllCollaborator(@RequestHeader("token") String token)
	{
		
		List<NoteInformation> noteList=serviceCollborator.getAllCollaborarors(token);
		
		return noteList;
		
	}
	/**
	 * 
	 * @param noteId
	 * @param email
	 * @param token
	 * @return noteInformation
	 */
	////Api for deleting collaborator
	@DeleteMapping("collaborator/deleteCollaborator")
	public ResponseEntity<UserResponse> deleteCollaborator(@RequestParam("noteId") Long noteId,@RequestParam("email") String email,@RequestHeader("token") String token)
	{
		Optional<NoteInformation> noteInformation=serviceCollborator.deleteCollaborator(noteId,email,token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"),202,noteInformation));
		
	}
}
