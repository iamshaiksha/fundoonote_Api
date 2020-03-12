/**
 * 
 */
package com.bridgelabz.fundoonotes.serviceImplementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.exception.LabelException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.LabelInformation;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserDao;
import com.bridgelabz.fundoonotes.repository.UserLabelRepository;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.util.JwtGenerator;

/**
 * @author shaik shaiksha vali
 *
 */
@Service
@PropertySource("classpath:Message.properties")
public class LabelServiceImplementattion implements LabelService{
	@Autowired
	private JwtGenerator generator;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserLabelRepository userlabelRepository;
	@Autowired
	private UserNoteRepository userNoteRepository;
	LabelInformation labelInformation=new LabelInformation();
	@Autowired
	private Environment environment;

	@Transactional
	@Override
	/* Method for creating the label */
	public LabelInformation createLabel(LabelDto labelDto, String token) {
		try {
			Long id=generator.parseJWT(token);
			UserInformation user=userDao.findUserById(id);


			if(user!=null)
			{
				//LabelInformation label=modelMapper.map(labelDto, LabelInformation.class);
				BeanUtils.copyProperties(labelDto,labelInformation);
				labelInformation.setName(labelDto.getName());
				user.getLables().add(labelInformation);

				return userlabelRepository.save(labelInformation);

			}
		}catch(Exception e)
		{
			throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
		}
		return null;

	}

	@Transactional
	@Override
	public List<LabelInformation> getAllLabels() {

		List<LabelInformation> list=new ArrayList<>();
		userlabelRepository.findAll().forEach(list::add);
		return list;
	}
	@Transactional
	@Override
	public LabelInformation getLabel(Long id) {

		LabelInformation labelInformation=userlabelRepository.findLabelById(id);

		return labelInformation;
	}

	@Transactional
	@Override
	public void deleteLabel(LabelUpdate labelUpdate, String token) {

		Long id=generator.parseJWT(token);
		UserInformation userInformation=userDao.findUserById(id);
		if(userInformation!=null)
		{
			try {
			LabelInformation labelInformation=userlabelRepository.findLabelById(labelUpdate.getLabelId());

			if(labelInformation!=null)
			{
				userlabelRepository.deleteById(labelInformation.getLabelId());
			}
			}
			catch (Exception e) {
				throw new LabelException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
			}

		}
	}

	@Transactional
	@Override
	public void update(LabelUpdate labelUpdate, String token) {
		try {
		Long id=generator.parseJWT(token);
		UserInformation userInformation=userDao.findUserById(id);
		if(userInformation!=null)
		{
			LabelInformation labelInformation=userlabelRepository.findLabelById(labelUpdate.getLabelId());
			if(labelInformation!=null)
			{
				labelInformation.setName(labelUpdate.getName());
				userlabelRepository.save(labelInformation);
			}

		}
		}catch (Exception e) {

			throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
		}

	}

	//	@Transactional
	//	@Override
	//	public void createMapLabel(LabelDto labelDto, String token) {
	//
	//		LabelInformation labelInformation=new LabelInformation();
	//		Long id=generator.parseJWT(token);
	//		UserInformation userInformation=userDao.findUserById(id);
	//		if(userInformation!=null)
	//		{
	//			//			LabelInformation labelInformation=modelMapper.map(labelDto,LabelInformation.class );
	//			//			labelInformation.setUserId(userInformation.getUserId());//labelId
	//			//			userlabelRepository.save(labelInformation);
	//			//			NoteInformation noteInformation=userNoteRepository.findNoteById(id);//NoteId
	//			//			noteInformation.getLabelList().add(labelInformation);
	//			//			userNoteRepository.save(noteInformation);
	//
	//
	//			BeanUtils.copyProperties(labelDto, LabelInformation.class);
	//			labelInformation.setUserId(userInformation.getUserId());
	//			userlabelRepository.save(labelInformation);
	//			Optional<NoteInformation> noteInformation=userNoteRepository.findById(id);
	//
	//			noteInformation.ifPresent(notes->{
	//
	//				
	//				notes.setNoteId(labelInformation.getLabelId());
	//				notes.setTitle(labelInformation.getName());
	//				notes.getLabelList().add(labelInformation);
	//				
	//			}
	//					);
	//
	//
	//		}
	//	}
	@Transactional
	@Override
	public LabelInformation addLabel(Long labelId, String token, Long noteId) {
		try {
		Long userId=generator.parseJWT(token);
		UserInformation userInformation=userDao.findUserById(userId);
		if(userInformation!=null)
		{
			NoteInformation noteInformation=userNoteRepository.findNoteById(noteId);
			LabelInformation labelInformation=userlabelRepository.findLabelById(labelId);
			noteInformation.getLabelList().add(labelInformation);
			//			labelInformation.getList().add(noteInformation);
			return userlabelRepository.save(labelInformation);
		}
		}catch (Exception e) {
			throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
		}
		return labelInformation;

	}
	@Transactional
	@Override
	public List<LabelInformation> getLabelsByUserId(String token) {

		Long id=generator.parseJWT(token);
		UserInformation userInformation=userDao.findUserById(id);
		System.out.println("-----------"+userInformation);
		if(userInformation!=null)
		{
			List<LabelInformation>label=userlabelRepository.findLabelByUserId(id);
			if(label!=null)
			{
				return label;
			}
		}
		return null;
	}
	@Transactional
	@Override
	public List<NoteInformation> getNotes(String token, Long labelId) {
		try
		{
		Long userId=generator.parseJWT(token);
		UserInformation userInformation=userDao.findUserById(userId);
		if(userInformation!=null)
		{
			LabelInformation labelInformation=userlabelRepository.findLabelById(labelId);
			if(labelInformation!=null)
			{
				List<NoteInformation> notes=userInformation.getNote();
				return notes;
			}
		}
		}catch (Exception e) {
			throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
		}

		return null;
	}
	@Transactional
	@Override
	public List<String> ascsortByName() {

		ArrayList<String> labelList = new ArrayList<>();
		List<LabelInformation> list = getAllLabels();
		list.forEach(data -> {
			labelList.add(data.getName());

		});
		Collections.sort(labelList);
		return labelList;		

	}
	@Transactional
	@Override
	public List<String> descsortByName() {
		ArrayList<String> labelList = new ArrayList<>();
		List<LabelInformation> list = getAllLabels();
		list.forEach(data -> {
			labelList.add(data.getName());

		});
		Collections.sort(labelList,Collections.reverseOrder());
		return labelList;	


	}


}
