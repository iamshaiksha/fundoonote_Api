package com.bridgelabz.fundoonotes.util;
/**
 * @author shaik shaiksha vali
 * 
 * */

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserInformation;
@Component
public class MailServiceProvider {
	@Autowired
	private JwtGenerator genarator;
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	static
	JavaMailSenderImpl mailSenderImplementation;
	
	
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
	
	
	public void send(Email emailid) {
		System.out.println("Sending mail to receiver");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailid.getTo());
		message.setSubject(emailid.getSubject());
		message.setText(emailid.getBody());
		mailSenderImplementation.send(message);
//		javaMailSender.send(message);

		System.out.println("email sent successfully");
	}
	public  String getlink(String link, Long id) {
		return link + genarator.jwtToken(id);
	}


	
	}

	
