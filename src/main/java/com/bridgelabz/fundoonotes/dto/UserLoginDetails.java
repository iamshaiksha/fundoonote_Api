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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginDetails {
	String email;
	String password;
}
