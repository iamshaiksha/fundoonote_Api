/**
 * 
 */
package com.bridgelabz.fundoonotes.service;


import java.util.List;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.model.LabelInformation;
import com.bridgelabz.fundoonotes.model.NoteInformation;

/**
 * @author shaik shaiksha vali
 *
 */

public interface LabelService {
	
	/**
	 * Used to create label
	 * @param labelDto
	 * @param token
	 * @return LabelInformation
	 */
	LabelInformation createLabel(LabelDto labelDto, String token);

	/**
	 * 
	 * @return List<LabelInformation>
	 */
	
	
	List<LabelInformation> getAllLabels();
	
	/**
	 * 
	 * @param id
	 * @return LabelInformation
	 */
	LabelInformation getLabel(Long id);

	void deleteLabel(String token, Long labelId);

	void update(LabelUpdate labelUpdate, String token);

	//void createMapLabel(LabelDto labelDto, String token);
	
	/**
	 * 
	 * @param labelId
	 * @param token
	 * @param noteId
	 * @return LabelInformatio
	 */
	LabelInformation addLabel(Long labelId, String token, Long noteId);
	
	/**
	 * 
	 * @param token
	 * @return List<LabelInformation>
	 */
	List<LabelInformation> getLabelsByUserId(String token);

	/**
	 * 
	 * @param token
	 * @param labelId
	 * @return List<NoteInformation>
	 */
	List<NoteInformation> getNotes(String token, Long labelId);
	
	/**
	 * 
	 * @return 	List<String>
	 */
	List<String> ascsortByName();
	
	/**
	 * 
	 * @return List<String> 
	 */
	
	List<String> descsortByName();

	String removeLable(Long noteId, String token, Long labelId);

	List<NoteInformation> getnotesfromlabel(String token, Long labelid);


	
	
	
}
