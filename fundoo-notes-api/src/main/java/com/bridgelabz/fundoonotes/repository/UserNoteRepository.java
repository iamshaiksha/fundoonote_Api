/**
 * 
 */
package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.NoteInformation;

/**
 * @author shaik shaiksha vali
 *
 */
@Repository
public interface UserNoteRepository extends CrudRepository<NoteInformation, Long> {

	@Query(value="Select * from note_information where note_id=?",nativeQuery=true)
	NoteInformation findNoteById(Long id);
	@Query(value = "select * from note_information where user_id=?", nativeQuery = true)
	List<NoteInformation> findNoteByUserId(Long id);
	@Query(value="Select * from note_information where note_id=?",nativeQuery=true)
	List<NoteInformation> findAllById(Long labelId);
	@Query(value="select * from  note_information where user_id=? AND is_archieved =1",nativeQuery = true)
	List<NoteInformation> getArchievedNotes(long userid);
	@Query(value="select * from  note_information where user_id=? AND is_pinned=1",nativeQuery = true)
	List<NoteInformation> getPinnededNotes(long userid);


}
