package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.UserLoginDetails;
import com.bridgelabz.fundoonotes.dto.UserPasswordUpdateDetails;
import com.bridgelabz.fundoonotes.dto.UserRegistrationDetails;
import com.bridgelabz.fundoonotes.model.UserInformation;


public interface UserService {
	Boolean register(UserRegistrationDetails information) throws Exception;

	UserInformation login(UserLoginDetails information);

	Boolean verify(String token);

	UserInformation forgetPassword(UserPasswordUpdateDetails userUpdate, String token);

//	Boolean update(UserPasswordUpdateDetails information, String token);

	List<UserInformation> getUsers();

	UserInformation getSingleUser(String token) throws Exception;

	String emailVerify(String emailId);

}
