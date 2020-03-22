package com.bridgelabz.fundoonotes.repository;
/**
 * @author shaik shaiksha vali
 * 
 * */
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.UserInformation;
@Repository
public interface UserDao extends CrudRepository<UserInformation, Long>{
//	@Query(value=" insert into user_information (date_time,email,is_verified,mobile_number,name,password) values (?,?,?,?,?,?,?)",nativeQuery=true)
//	UserInformation save(UserInformation information);
	@Query(value=" select * from user_information where user_id=?",nativeQuery=true)
	UserInformation findUserById(Long id);
	@Query(value=" select * from user_information where email=?",nativeQuery=true)
	UserInformation getUser(String Email);
//	@Query(value=" update user_information set password=? where email=?",nativeQuery=true)
//	boolean update(UserPasswordUpdateDetails information, Long id);
	@Query(value=" select * from user_information",nativeQuery=true)
	List<UserInformation> getUsers();
	@Query(value=" update user_information set is_verified=true where user_id=?",nativeQuery=true)
	boolean verify(Long id);
	@Query(value=" select * from user_information where name=?",nativeQuery=true)
	List<UserInformation> findByName(String text);
	@Query(value=" select * from user_information where email=?",nativeQuery=true)
	Optional<UserInformation> findByEmailId(String email);

}
