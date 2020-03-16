package com.bridgelabz.fundoonotes.serviceImplementation;
/**
 * @author shaik shaiksha vali
 * 
 * */
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.config.AppConfig;
import com.bridgelabz.fundoonotes.dto.UserLoginDetails;
import com.bridgelabz.fundoonotes.dto.UserPasswordUpdateDetails;
import com.bridgelabz.fundoonotes.dto.UserRegistrationDetails;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserDao;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.util.JwtGenerator;
import com.bridgelabz.fundoonotes.util.MailServiceProvider;

@Service
@PropertySource("classpath:Message.properties")
public class UserServiceImpl implements UserService {
	UserInformation userInformation = new UserInformation();
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private BCryptPasswordEncoder encryption;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MailResponse response;
	@Autowired
	private MailObject mailObject;
	@Autowired
	private UserDao repository;
	@Autowired
	private JavaMailSenderImpl mailSenderImplementation;
	@Autowired
	private Environment environment;
	@Autowired
	RabbitMQSender rabbitMQSender;	



	/* Method for user registration */
	@Transactional
	@Override
	public Boolean register(UserRegistrationDetails information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user == null) {
			userInformation = modelMapper.map(information, UserInformation.class);
			userInformation.setDateTime(LocalDateTime.now());
			String epassword = encryption.encode(information.getPassword());
			userInformation.setPassword(epassword);
			userInformation.setIsVerified(0);
			repository.save(userInformation);
			rabbitMQSender.send(userInformation);
			this.mailSenderService();
			MailServiceProvider.sendMail(userInformation,mailSenderImplementation,generate.jwtToken(userInformation.getUserId()));
			
//			String mailResponse = response.fromMessage("http://localhost:8080 verify",
//					generate.jwtToken(userInformation.getUserId()));
//			mailObject.setEmail(information.getEmail());
//			mailObject.setMessage(mailResponse);
//			mail.sendMail(information.getEmail(), mailResponse);

			System.out.println(generate.jwtToken(userInformation.getUserId()));
			return true;
		}
		throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));

	}
	public JavaMailSenderImpl mailSenderService()
	{
		mailSenderImplementation.setUsername(System.getenv("email"));
		mailSenderImplementation.setPassword(System.getenv("password"));
		mailSenderImplementation.setPort(587);
		Properties properties=new Properties();
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		mailSenderImplementation.setJavaMailProperties(properties);
		return mailSenderImplementation;
	}

	/* Method for user login */

	@Transactional
	@Override
	public UserInformation login(UserLoginDetails information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user != null) {
			if ((user.getIsVerified() == 0) && (encryption.matches(information.getPassword(), user.getPassword()))) {
				System.out.println(generate.jwtToken(user.getUserId()));
				return user;
			}
		}
		else
		{
			throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
		}
		return null;
	}

	public String generateToken(Long id) {
		return generate.jwtToken(id);

	}
	/* Method for verifying the user */

	@Transactional
	@Override
	public Boolean verify(String token) {
		System.out.println("id is in verification" + (Long) generate.parseJWT(token));
		Long id = (Long) generate.parseJWT(token);
		
		UserInformation user = repository.findUserById(id);
		user.setIsVerified(0);
		repository.save(user);
		return true;
	}
	@Transactional
	@Override
	public String emailVerify(String email) {
		UserInformation user = repository.getUser(email);
		if(user!=null)
		{
		this.mailSenderService();
		String token = generate.jwtToken(user.getUserId());
		MailServiceProvider.sendMail(user, mailSenderImplementation,token);
		return token;
		}
		else
		{
			throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
		}
		
	}
	/* Method to save new Password */

	@Transactional
	@Override
	public UserInformation forgetPassword(UserPasswordUpdateDetails userUpdate,String token) {
		try {
			
			UserInformation userInformation=modelMapper.map(userUpdate, UserInformation.class);
			userInformation.setPassword(encryption.encode(userUpdate.getNewPassword()));
			return repository.save(userInformation);
		} catch (Exception e) {
			throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
		}

	}
//	/* Method for updating the user information */
//
//	@Transactional
//	@Override
//	public Boolean update(UserPasswordUpdateDetails information, String token) {
//		try {
//			Long id = null;
//			id = (Long) generate.parseJWT(token);
//			String epassword = encryption.encode(information.getConfirmPassword());
//			String epassword1 = encryption.encode(information.getNewPassword());
//			if (epassword == epassword1) {
//				information.setConfirmPassword(epassword);
//			}
//			return repository.update(information, id);
//		} catch (Exception e) {
//			throw new UserException("Invalid data ");
//		}
//
//	}
//	/* Method for list out all the userIformation */

	@Transactional
	@Override
	public List<UserInformation> getUsers() {
		List<UserInformation> users = repository.getUsers();
		//UserInformation user = users.get(0);
		//System.out.println(user);
		return users;
	}

	/* Method to get the single userInformation  */
	@Transactional
	@Override
	@Cacheable(value="twenty-second-cache", key = "'tokenInCache'+#token", 
    condition = "#isCacheable != null && #isCacheable")
	public UserInformation getSingleUser(String token,boolean cacheable) {
		try {
			Long id = (Long) generate.parseJWT(token);

			UserInformation user = repository.findUserById(id);

			return user;
		} catch (Exception e) {
			throw new UserException(HttpStatus.NOT_FOUND,environment.getProperty("404"));
		}
	}
	

}
