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

	LabelInformation createLabel(LabelDto labelDto, String token);

	List<LabelInformation> getAllLabels();

	LabelInformation getLabel(Long id);

	void deleteLabel(LabelUpdate labelUpdate, String token);

	void update(LabelUpdate labelUpdate, String token);

	//void createMapLabel(LabelDto labelDto, String token);

	LabelInformation addLabel(Long labelId, String token, Long noteId);

	List<LabelInformation> getLabelsByUserId(String token);

	List<NoteInformation> getNotes(String token, Long labelId);

	List<String> ascsortByName();

	List<String> descsortByName();


	
	
	
}
