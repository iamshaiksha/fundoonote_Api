/**
 * 
 */
package com.bridgelabz.fundoonotes.serviceImplementation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserDao;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.service.ServiceCollaborator;
import com.bridgelabz.fundoonotes.util.JwtGenerator;

/**
 * @author shaik shaiksha vali
 */
@Service
@PropertySource("classpath:Message.properties")
public class serviceCollaboratorImplementation implements ServiceCollaborator{
	@Autowired
	private UserDao userDao;
	@Autowired
	private JwtGenerator generator;
	private UserInformation userInformation=new UserInformation();
	@Autowired
	private UserNoteRepository userNoteRepository;
	@Autowired
	private Environment environment;
	@Transactional
	@Override
	public Optional<NoteInformation> addCollaborator(Long noteId, String email, String token) {
		UserInformation collaborator=userDao.getUser(email);
		try
		{
			Long userId=generator.parseJWT(token);
			userInformation=userDao.findUserById(userId);
		}
		catch(Exception e)
		{
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));
		}
		if(userInformation!=null)
		{
			if(collaborator!=null)
			{
				Optional<NoteInformation> noteInformation=userNoteRepository.findById(noteId);
				noteInformation.ifPresent(notes->
					{
					collaborator.getCollaboratorNote().add(notes);
					}
				);
				return noteInformation;
			}
			else
			{
				throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));
			}
		}
		else
		{
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));
		}
	}
	@Transactional
	@Override
	public List<NoteInformation> getAllCollaborarors(String token) {
		
		Long id=generator.parseJWT(token);
		UserInformation userInformation=userDao.findUserById(id);
		if(userInformation!=null)
		{
			List<NoteInformation> notes=userInformation.getCollaboratorNote();
			return notes;
		}
		else
		{
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));
		}
		
		
	}
	@Transactional
	@Override
	public Optional<NoteInformation> deleteCollaborator(Long noteId, String email, String token) {
		UserInformation collaborator=userDao.getUser(email);
		try
		{
			Long userId=generator.parseJWT(token);
			userInformation=userDao.findUserById(userId);
		}
		catch(Exception e)
		{
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));
		}
		if(userInformation!=null)
		{
			if(collaborator!=null)
			{
				Optional<NoteInformation> noteInformation=userNoteRepository.findById(noteId);
				noteInformation.ifPresent(notes->
				{	System.out.println(notes);
					collaborator.getCollaboratorNote().remove(notes);
					
				}
				);
				return noteInformation;
			}
		}
		
		return null;
	}



}
