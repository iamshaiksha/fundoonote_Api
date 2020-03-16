/**
 * 
 */
package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.ProfilePic;

/**
 * @author shaik shaiksha vali
 *
 */
@Repository
public interface UserProfileRepository extends CrudRepository<ProfilePic, Long>{
//	@Query(value="Select * from profile where user_id=?",nativeQuery=true)
//	ProfilePic findByUserId(Long userId);
	@Query(value="Select * from profile where user_id=?",nativeQuery=true)
	ProfilePic findUserById(Long userId);

	
	
	
}