package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.fundoonotes.dto.ForgetPasswordDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDetails;
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
	String login(UserLoginDetails information);
	/**
	 * 
	 * @param token
	 * @return Boolean
	 */
	Boolean verify(String token);

	/**
	 * 
	 * @param token
	 * 
	 */

	String forgetPassword(String token);
	/**
	 * 
	 * @return Boolean
	 */
	
	/**
	 * 
	 * @param token
	 * @param forgetPasswordDto
	 * @return
	 */
	String resetpassword(@Valid String token, ForgetPasswordDto forgetPasswordDto);


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
