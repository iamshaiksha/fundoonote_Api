package com.bridgelabz.fundoonotes.config;

/**
 * @author shaik shaiksha vali
 * 
 * */
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bridgelabz.fundoonotes.util.MailServiceProvider;


@Configuration
public class AppConfig {

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public MailServiceProvider getSpringMail() {
		return new MailServiceProvider();

	}
	@Bean
	public JavaMailSenderImpl mail()
	{
		return new JavaMailSenderImpl();
	}

}
