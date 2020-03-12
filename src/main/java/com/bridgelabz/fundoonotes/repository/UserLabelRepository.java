/**
 * 
 */
package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.LabelInformation;
import com.bridgelabz.fundoonotes.model.NoteInformation;

/**
 * @author shaik shaiksha vali
 *
 */
@Repository
public interface UserLabelRepository extends CrudRepository<LabelInformation, Long> {

	@Query(value="select * from label_information where label_id=?",nativeQuery=true)
	LabelInformation findLabelById(Long labelId);
	@Query(value="select * from label_information where user_id=?",nativeQuery=true)
	List<LabelInformation> findLabelByUserId(Long id);
	@Query(value="select * from note_labels where label_id= ?",nativeQuery = true)
	List<NoteInformation> getAllNotes(Long labelId);

	
	
	
	
}
