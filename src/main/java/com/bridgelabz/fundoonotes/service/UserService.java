package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.UserLoginDetails;
import com.bridgelabz.fundoonotes.dto.UserPasswordUpdateDetails;
import com.bridgelabz.fundoonotes.dto.UserRegistrationDetails;
import com.bridgelabz.fundoonotes.model.UserInformation;


public interface UserService {
	/**
	 * 
	 * @param information
	 * @return
	 * @throws Exception
	 */
	Boolean register(UserRegistrationDetails information) throws Exception;

	/**
	 * 
	 * @param information
	 * @return UserInformatio
	 */
	UserInformation login(UserLoginDetails information);
	/**
	 * 
	 * @param token
	 * @return Boolean
	 */
	Boolean verify(String token);

	/**
	 * 
	 * @param userUpdate
	 * @param token
	 * @return UserInformation
	 */

	UserInformation forgetPassword(UserPasswordUpdateDetails userUpdate, String token);
	/**
	 * 
	 * @return Boolean
	 */

	//	Boolean update(UserPasswordUpdateDetails information, String token);

	/**
	 * 
	 * @return List<UserInformation>
	 */
	List<UserInformation> getUsers();
	/**
	 * 
	 * @param token
	 * @param cacheable 
	 * @return
	 * @throws Exception
	 */
	UserInformation getSingleUser(String token, boolean cacheable) throws Exception;
	/**
	 * 
	 * @param emailId
	 * 
	 */
	String emailVerify(String emailId);

}
