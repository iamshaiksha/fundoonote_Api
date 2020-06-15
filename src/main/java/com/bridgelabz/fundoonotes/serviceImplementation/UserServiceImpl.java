package com.bridgelabz.fundoonotes.serviceImplementation;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * @author shaik shaiksha vali
 * 
 * */
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.ForgetPasswordDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDetails;
import com.bridgelabz.fundoonotes.dto.UserRegistrationDetails;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserDao;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.util.Email;
import com.bridgelabz.fundoonotes.util.JwtGenerator;
import com.bridgelabz.fundoonotes.util.MailServiceProvider;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;
import java.net.URL;
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
	@Autowired
	private MailServiceProvider mailService;
	

	private final Path rootLocation = Paths.get("src/main/resources/profileImages");


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
			String token=generate.jwtToken(userInformation.getUserId());
			rabbitMQSender.send(token);
			this.mailSenderService();
			MailServiceProvider.sendMail(userInformation,mailSenderImplementation,token);
			
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
	public String login(UserLoginDetails information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user != null) {
			if ((user.getIsVerified() == 0) && (encryption.matches(information.getPassword(), user.getPassword()))) {
				String token=generate.jwtToken(user.getUserId());
				System.out.println(token);
				return token;
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
	public String forgetPassword(String email) {
		Email emailobj = new Email();
		Optional<UserInformation> optionalUser = repository.findByEmailId(email);
		return optionalUser.filter(user -> {
			return user != null;
		}).map(user -> {
			emailobj.setEmailId("iamshaiksha2019@gmail.com");
			emailobj.setTo(email);
			emailobj.setSubject("ChangeLink");
			emailobj.setBody(mailService.getlink("http://localhost:4200/resetPassword/", user.getUserId()));
			this.mailSenderService();
			mailService.send(emailobj);
			return environment.getProperty("user.forget.password");
		}).orElseThrow(() -> new UserException(null, environment.getProperty("user.forget.password.fail")));

	}
	@Override
	public String resetpassword(@Valid String token, ForgetPasswordDto forgetPasswordDto) {
		Long id = generate.parseJWT(token);
		Optional<UserInformation> optionalUser = repository.findById(id);
		return optionalUser.filter(user -> {
			return user != null;
		}).map(user -> {
			user.setPassword(encryption.encode(forgetPasswordDto.getPassword()));
			
			repository.save(user);
			return environment.getProperty("user.restpassword.change");
		}).orElseThrow(() -> new UserException(null, "user.restpassword.changeInfo"));
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
	@Override
	public UserInformation getUserByUserName(String userName) {


		UserInformation user= repository.findByUserName(userName);
						   
		return user;
	}
	       		
	}
	


