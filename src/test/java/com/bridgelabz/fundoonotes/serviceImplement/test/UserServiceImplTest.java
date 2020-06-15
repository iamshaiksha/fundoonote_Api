//package com.bridgelabz.fundoonotes.serviceImplement.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.core.env.Environment;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.bridgelabz.fundoonotes.model.UserInformation;
//import com.bridgelabz.fundoonotes.repository.UserDao;
//import com.bridgelabz.fundoonotes.response.MailObject;
//import com.bridgelabz.fundoonotes.response.MailResponse;
//import com.bridgelabz.fundoonotes.serviceImplementation.RabbitMQSender;
//import com.bridgelabz.fundoonotes.serviceImplementation.UserServiceImpl;
//import com.bridgelabz.fundoonotes.util.JwtGenerator;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceImplTest {
//
//	
//	@Mock
//	private JwtGenerator generate;
//	@Mock
//	private BCryptPasswordEncoder encryption;
//	@Mock
//	private ModelMapper modelMapper;
//	@Mock
//	private MailResponse response;
//	@Mock
//	private MailObject mailObject;
//	@Mock
//	private UserDao repository;
//	@Mock
//	private JavaMailSenderImpl mailSenderImplementation;
//	@Mock
//	private Environment environment;
//	@Mock
//	RabbitMQSender rabbitMQSender;
//	
//	
//	
//	@Test
//	public void testGetUsers() throws Exception {
//		UserServiceImpl userServiceImpl=new UserServiceImpl();
//		List<UserInformation> users=new ArrayList<>();
//		users.add(getUserInformation());
//		Mockito.when(repository.getUsers()).thenReturn(users);
//		List<UserInformation> response= userServiceImpl.getUsers();
//		response.forEach(System.out::println);
//	}
//	
//	public UserInformation getUserInformation() {
//		UserInformation userInformation=new UserInformation();
//		userInformation.setIsVerified(1);
//		userInformation.setUserId(123L);
//		userInformation.setEmail("test@gmail.com");
//		userInformation.setName("test");
//		return userInformation;
//	}
//	
//}
