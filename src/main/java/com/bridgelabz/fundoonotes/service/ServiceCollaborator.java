
package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotes.model.NoteInformation;

/**
 * @author Shaik shaiksha vali
 *
 */
public interface ServiceCollaborator {
	/**
	 * 
	 * @param noteId
	 * @param email
	 * @param token
	 * @return Optional<NoteInformation>
	 */

	Optional<NoteInformation> addCollaborator(Long noteId, String email, String token);

	/**
	 * 
	 * @param token
	 * @return 	List<NoteInformation>
	 */
	List<NoteInformation> getAllCollaborarors(String token);
	/**
	 * 
	 * @param noteId
	 * @param email
	 * @param token
	 * @return Optional<NoteInformation>
	 */
	Optional<NoteInformation> deleteCollaborator(Long noteId, String email, String token);

}
