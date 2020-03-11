/**
 * 
 */
package com.bridgelabz.fundoonotes.serviceImplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNoteDto;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserDao;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.util.JwtGenerator;

/**
 * @author shaik shaiksha vali
 *
 */
@Service
@PropertySource("classpath:Message.properties")
public class NoteServiceImplementation implements NoteService{
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private Environment environment;
	UserInformation userInformation=new UserInformation();
	@Autowired
	private UserNoteRepository userNoteRepository;
	@Autowired
	private UserDao userDao;
//	@Autowired
//	private ModelMapper modelMapper;
	NoteInformation noteInformation=new NoteInformation();

	@Transactional
	@Override
	public NoteInformation create(UserNoteDto userNoteDto, String token) {
		try
		{
			Long id=(Long)generate.parseJWT(token);
			System.out.println("id="+id);
			UserInformation user=userDao.findUserById(id);
			if(user!=null)
			{
				//NoteInformation noteInformation=modelMapper.map(userNoteDto,NoteInformation.class);
				BeanUtils.copyProperties(userNoteDto,noteInformation);
				noteInformation.setCreateDateTime(LocalDateTime.now());
				noteInformation.setIsArchieved(0);
				noteInformation.setColor("white");
				noteInformation.setIsPinned(0);
				noteInformation.setIsTrashed(0);
				noteInformation.setReminder(null);
				noteInformation.setUpDateTime(null);
				noteInformation.setDescription(userNoteDto.getDescription());
				noteInformation.setTitle(userNoteDto.getTitle());
				user.getNote().add(noteInformation);
				return userNoteRepository.save(noteInformation);
			}
		}
		catch(Exception e)
		{
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("400"));
		}
		return noteInformation;
	}

	@Transactional
	@Override
	public List<NoteInformation> getAllNotes() {

		List<NoteInformation> noteList=new ArrayList<>();
		userNoteRepository.findAll().forEach(noteList::add);
		return noteList;

	}
	@Transactional
	@Override
	public NoteInformation getNote(String id1) {
		Long id=Long.parseLong(id1);
		NoteInformation noteInformation=userNoteRepository.findNoteById(id);
		return noteInformation;
	}
	@Transactional
	@Override
	public List<NoteInformation> updateNote(UpdateNote updateNoteDto, String token) {

		List<NoteInformation> list=new ArrayList<>();
		try
		{
			Long id=generate.parseJWT(token);
			userInformation=userDao.findUserById(id);
			Optional<NoteInformation> noteInformation= userNoteRepository.findById(updateNoteDto.getId());

			if(noteInformation!=null)
			{
				noteInformation.ifPresent(notes->{

					notes.setNoteId(updateNoteDto.getId());
					notes.setTitle(updateNoteDto.getTitle());
					notes.setDescription(updateNoteDto.getDescription());
					notes.setIsArchieved(updateNoteDto.getIsArchieved());
					notes.setIsPinned(updateNoteDto.getIsPinned());
					notes.setIsTrashed(updateNoteDto.getIsTrashed());
					notes.setUpDateTime(LocalDateTime.now());
					//notes.setUserInformation(userInformation);
					//notes.getUserInformation();
					userNoteRepository.save(notes);


				}		
						);
			}

		}catch (Exception e) {
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));

		}

		return list;
	}
	@Transactional
	@Override
	public void deleteNote(Long id, String token) {
		try
		{
			Long userId=generate.parseJWT(token);
			userInformation=userDao.findUserById(userId);
			noteInformation = userNoteRepository.findNoteById(userId);
			if(noteInformation!=null)
			{
				noteInformation.setIsTrashed(0);
				userNoteRepository.save(noteInformation);
			}
		}catch(Exception e)
		{
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));
		}

	}
	@Transactional
	@Override
	public void addColour(Long noteId, String colour) {

		try {
			Optional<NoteInformation> noteInformation = userNoteRepository.findById(noteId);
			if (noteInformation != null) 
				noteInformation.ifPresent(notes->{
					notes.setColor("cyan");
					userNoteRepository.save(notes);
				});

		} catch (Exception e) {
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));
		}
	}
	@Transactional
	@Override
	public List<NoteInformation> getNoteByUserId(String token) {
		
		Long id =  generate.parseJWT(token);
		
		try {
			 List<NoteInformation> user = userNoteRepository.findNoteByUserId(id);
			if (user != null) {
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public NoteInformation pinned(Long noteId, String token) {
		Long id=generate.parseJWT(token);
		List<NoteInformation> noteList = userNoteRepository.findNoteByUserId(id);
		try {
			if (noteList!= null) {
				NoteInformation data =noteList.stream().filter(note->note.getNoteId()==noteId).findFirst().orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,"Note not exist on the user"));
				data.setIsPinned(1);
				data.setIsArchieved(0);
				data.setUpDateTime(LocalDateTime.now());
				return userNoteRepository.save(data);


			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Pinned due to Internal Error");
		}
		return noteInformation;
	}
	@Transactional
	@Override
	public NoteInformation archieveNote(Long noteId, String token) {
		Long id=generate.parseJWT(token);
		List<NoteInformation> noteList = userNoteRepository.findNoteByUserId(id);

		if(noteList.isEmpty())
			return null;
		try {
			NoteInformation data = noteList.stream().filter(t -> t.getNoteId() == noteId).findFirst()
					.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,environment.getProperty("400")));
			
			data.setIsPinned(0);
			data.setIsArchieved(1);
			data.setUpDateTime(LocalDateTime.now());
			return userNoteRepository.save(data);
			
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty("500"));
		}
	}
	@Transactional
	@Override
	public NoteInformation addReminder(Long noteId, String token, RemainderDto remainder) {

		Long id=generate.parseJWT(token);
		List<NoteInformation> noteList = userNoteRepository.findNoteByUserId(id);
		
		try {
			if (noteList != null) {
				NoteInformation noteData = noteList.stream().filter(t -> t.getNoteId() == noteId).findFirst()
						.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,environment.getProperty("400")));
					noteData.setReminder(remainder.getRemainder());
					return userNoteRepository.save(noteData);
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty("500"));
		}
		return noteInformation;

	}
	@Override
	public NoteInformation removeRemainder(Long noteId, String token) {
		
		Long id=generate.parseJWT(token);
		List<NoteInformation> noteList = userNoteRepository.findNoteByUserId(id);
		try {
			if (noteList != null) {
				NoteInformation noteData = noteList.stream().filter(t -> t.getNoteId() == noteId).findFirst()
						.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,environment.getProperty("400")));
					noteData.setReminder(null);
					return userNoteRepository.save(noteData);
			
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty("500"));
		}
		return noteInformation;
	}
	@Transactional
	@Override
	public List<NoteInformation> getarchieved(String token) {

		try {
			long userid =  generate.parseJWT(token);
			UserInformation userData = userDao.findUserById(userid);
			if(userData!=null)
			{
				return  userNoteRepository.getArchievedNotes(userid);
			}	
			else
			{
				throw new NoteException(HttpStatus.BAD_REQUEST,environment.getProperty("400"));
			}
		} catch (Exception e) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty("500"));
		}

		
	}
	@Transactional
	@Override
	public List<NoteInformation> getAllPinneded(String token) {
		try {
			long userid =  generate.parseJWT(token);
			UserInformation userData = userDao.findUserById(userid);
			if(userData!=null)
			{
				List<NoteInformation> list = userNoteRepository.getPinnededNotes(userid);
				return list;
			}
			else
			{
				throw new NoteException(HttpStatus.BAD_REQUEST,environment.getProperty("400"));
			}
		} catch (Exception e) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty("500"));
		}
	}
	@Transactional
	@Override
	public List<String> ascSortByName() {

		ArrayList<String> noteTitles = new ArrayList<>();
		List<NoteInformation> Notelist = getAllNotes();
		Notelist.forEach(t -> {
			noteTitles.add(t.getTitle());
		});
//		for (NoteInformation noteInformation : Notelist) {
//			noteTitles.add(noteInformation.getTitle());
//		}
		Collections.sort(noteTitles);
		return noteTitles;
		
	}
	@Transactional
	@Override
	public List<String> descsortByName() {
		ArrayList<String> noteTitles = new ArrayList<>();
		List<NoteInformation> Notelist = getAllNotes();
		Notelist.forEach(t -> {
			noteTitles.add(t.getTitle());
		});
//		for (NoteInformation noteInformation : Notelist) {
//			noteTitles.add(noteInformation.getTitle());
//		}
		Collections.sort(noteTitles,Collections.reverseOrder());
		return noteTitles;
	}
}



