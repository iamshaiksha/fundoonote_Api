package com.bridgelabz.fundoonotes.util;
/**
 * @author shaik shaiksha vali
 * 
 * */

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.model.UserInformation;
@Component
public class MailServiceProvider {
	
	
	
	public static void sendMail(UserInformation userInformation, JavaMailSenderImpl mailSenderImplementation, String jwtToken) {
		// TODO Auto-generated method stub
		try {
			SimpleMailMessage simpleMsg = new SimpleMailMessage();
			simpleMsg.setTo(userInformation.getEmail());
			simpleMsg.setSubject("Verify mail");
			simpleMsg.setText("Hii..!"+userInformation.getName()+"verify the user:\n"+"http://8080/verify/"+jwtToken);
			mailSenderImplementation.send(simpleMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
