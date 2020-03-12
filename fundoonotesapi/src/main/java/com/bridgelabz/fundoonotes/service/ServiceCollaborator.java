/**
 * 
 */
package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotes.model.NoteInformation;

/**
 * @author Shaik shaiksha vali
 *
 */
public interface ServiceCollaborator {

	Optional<NoteInformation> addCollaborator(Long noteId, String email, String token);

	List<NoteInformation> getAllCollaborarors(String token);

	Optional<NoteInformation> deleteCollaborator(Long noteId, String email, String token);

}
