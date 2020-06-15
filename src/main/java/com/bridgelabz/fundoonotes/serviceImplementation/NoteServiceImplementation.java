/**
 * 
 */
package com.bridgelabz.fundoonotes.serviceImplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.bridgelabz.fundoonotes.model.LabelInformation;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserDao;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.service.UserServiceSearch;
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
	@Autowired(required = true)
	private UserServiceSearch userServiceSearch;
	@Autowired
	private Environment environment;
	UserInformation userInformation=new UserInformation();
	@Autowired
	private UserNoteRepository userNoteRepository;
	@Autowired
	private UserDao userDao;
//	@Autowired
//	 public NoteServiceImplementation(UserServiceSearch userServiceSearch) {
//	        this.userServiceSearch = userServiceSearch;
//	    }
	//	@Autowired
	//	private ModelMapper modelMapper;
	NoteInformation note=new NoteInformation();

	@Transactional
	@Override
	public NoteInformation create(UserNoteDto userNoteDto, String token) {
		
NoteInformation note=new NoteInformation();
		Long id=(Long)generate.parseJWT(token);
		System.out.println("id="+id);
		UserInformation user=userDao.findUserById(id);
		//Optional<UserInformation> optionalUser=userDao.find
//		return optionalUser.filter(userInfo->userInfo!=null).filter(note->{
//			return userNoteDto.getTitle()!=null&& userNoteDto.getTitle()!=null;})
//				.map(userInfo->{
					BeanUtils.copyProperties(userNoteDto,note);
					note.setCreateDateTime(LocalDateTime.now());
					note.setIsArchieved(1);
					note.setColor("white");
					note.setIsPinned(1);
					note.setIsTrashed(1);
					note.setReminder(null);
					note.setUpDateTime(LocalDateTime.now());
					note.setDescription(userNoteDto.getDescription());
					note.setTitle(userNoteDto.getTitle());
					user.getNote().add(note);
				 NoteInformation notes=userNoteRepository.save(note);
				 try {
					 userServiceSearch.createNote(notes);
					} catch (Exception e) {
						e.printStackTrace();
					}
				 return notes;
				 
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
	public NoteInformation getNote(long id1) {
//		Long id=Long.parseLong(id1);
		NoteInformation noteInformation=userNoteRepository.findNoteById(id1);
		return noteInformation;
	}
	@Transactional
	@Override
	public List<NoteInformation> updateNote(UpdateNote updateNoteDto, String token) {

		List<NoteInformation> list=new ArrayList<>();

		Long id=generate.parseJWT(token);
		userInformation=userDao.findUserById(id);
		Optional<NoteInformation> noteInformation= userNoteRepository.findById(updateNoteDto.getNoteId());
		return noteInformation.filter(note -> {
			return note != null;
		}).map(note->{
		note.setTitle(updateNoteDto.getTitle());
		note.setDescription(updateNoteDto.getDescription());
//		note.setIsTrashed(updateNoteDto.getIsTrashed());
//		note.setColor(updateNoteDto.getColor());
//		note.setUpDateTime(LocalDateTime.now());
		NoteInformation notes=userNoteRepository.save(note);
		
		
		try {
			userServiceSearch.upDateNote(notes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		}).orElseThrow(()->new UserException(HttpStatus.BAD_REQUEST,environment.getProperty("404")));
	}

	@Transactional
	@Override
	public NoteInformation deleteNote(String token, Long noteId) {
		try
		{	NoteInformation noteInformation=new NoteInformation();
			Long userId=generate.parseJWT(token);
			userInformation=userDao.findUserById(userId);
			List<NoteInformation> notes = userNoteRepository.findNoteByUserId(userId);
//			if(notes!=null)
//				System.out.println("#############################"+note);
//				userNoteRepository.delete(notes);
//				noteInformation.setIsTrashed(0);
//				userNoteRepository.save(notes);
			NoteInformation data = notes.stream().filter(t -> t.getNoteId() == noteId).findFirst()
					.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, environment.getProperty("404")));
			try {
				data.setIsTrashed(0);
//				noteInformation.setIsArchieved(1);
				 NoteInformation note= userNoteRepository.save(data);
//				userNoteRepository.delete(data);
				 userServiceSearch.deleteNote(String.valueOf(noteId));
				return note; 

			} catch (Exception ae) {
				throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, environment.getProperty("500"));
			}
			
			
		}catch(Exception e)
		{
			throw new UserException(HttpStatus.BAD_GATEWAY,environment.getProperty("502"));
		}
		
	}

	
	@Transactional
	@Override
	public String addColour(Long noteId, String color) {

		
			Optional<NoteInformation> optionalNote = userNoteRepository.findById(noteId);
			return optionalNote.filter(note->{return note!=null;}).
			map(note->{
			note.setColor(color);
			note.setUpDateTime(LocalDateTime.now());
			NoteInformation notes=userNoteRepository.save(note);
			try {
				userServiceSearch.upDateNote(note);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return environment.getProperty("201");
			}).orElseThrow(()->new UserException(HttpStatus.BAD_REQUEST,environment.getProperty("404")));
		 
	}
	@Transactional
	@Override
	public List<NoteInformation> getNoteByUserId(String token) {

		Long id =  generate.parseJWT(token);

		try {
			List<NoteInformation> user = userNoteRepository.findNoteByUserId(id);
			if (user != null) {
				System.out.println("note........."+user);
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
				data.setIsPinned(0);
//				data.setIsArchieved(1);
				data.setUpDateTime(LocalDateTime.now());
				NoteInformation note= userNoteRepository.save(data);
				userServiceSearch.upDateNote(note);
				return note;
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Pinned due to Internal Error");
		}
		return note;
	}
	@Transactional
	@Override
	public NoteInformation archieveNote( String token,long noteId) {
		Long id=generate.parseJWT(token);
		List<NoteInformation> noteList = userNoteRepository.findNoteByUserId(id);

		if(noteList.isEmpty())
			return null;
		try {
			NoteInformation data = noteList.stream().filter(t -> t.getNoteId() == noteId).findFirst()
					.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,environment.getProperty("400")));

//			data.setIsPinned(1);
			data.setIsArchieved(0);
			data.setUpDateTime(LocalDateTime.now());
			NoteInformation note=userNoteRepository.save(data);
			userServiceSearch.upDateNote(note);
			return note;
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty("500"));
		}
	}
	@Transactional
	@Override
	public NoteInformation addReminder(Long noteId, String token, RemainderDto remainder) {

		Long id=generate.parseJWT(token);
		List<NoteInformation> noteList = userNoteRepository.findNoteByUserId(id);

		
			if (noteList != null) {
				NoteInformation noteData = noteList.stream().filter(t -> t.getNoteId() == noteId).findFirst()
						.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,environment.getProperty("400")));
				noteData.setReminder(remainder.getRemainder());
				NoteInformation note= userNoteRepository.save(noteData);
				try {
					userServiceSearch.upDateNote(note);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return note;
			}
			return note;
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
				NoteInformation note= userNoteRepository.save(noteData);
				userServiceSearch.upDateNote(note);
				return note;
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty("500"));
		}
		return note;
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
	@Transactional
	@Override
	public List<LabelInformation> getLabelsFromNote(Long noteId, String token) {
		// TODO Auto-generated method stub
		long id =  generate.parseJWT(token);		
		NoteInformation note = userNoteRepository.findNoteById(id);
		if (note.getLabelList() != null) {
			List<LabelInformation> labels = note.getLabelList().stream().collect(Collectors.toList());
			return labels;
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public List<NoteInformation> getAlltrashednotes(String token) {
		try {
		long userid =  generate.parseJWT(token);
		UserInformation userData = userDao.findUserById(userid);		
		if(userData!=null)
		{
			return  userNoteRepository.restoreNote(userid);
		}	
		else
		{
			throw new NoteException(HttpStatus.BAD_REQUEST,environment.getProperty("400"));
		}
	} catch (Exception e) {
		throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,environment.getProperty("500"));
	}

	}

	@Override
	public NoteInformation unpinned(Long noteId, String token) {
		Long id=generate.parseJWT(token);
		List<NoteInformation> noteList = userNoteRepository.findNoteByUserId(id);
		try {
			if (noteList!= null) {
				NoteInformation data =noteList.stream().filter(note->note.getNoteId()==noteId).findFirst().orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,"Note not exist on the user"));
				data.setIsPinned(1);
//				data.setIsArchieved(0);
				data.setUpDateTime(LocalDateTime.now());
				return userNoteRepository.save(data);
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Pinned due to Internal Error");
		}
		return note;
}

	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	
}

