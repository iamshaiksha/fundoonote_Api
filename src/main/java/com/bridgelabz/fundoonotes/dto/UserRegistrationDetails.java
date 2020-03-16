package com.bridgelabz.fundoonotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shaik shaiksha vali
 * 
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegistrationDetails {
	private String name;
	private String email;
	private String password;
	
	private String mobileNumber;

}
