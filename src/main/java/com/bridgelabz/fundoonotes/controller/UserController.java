package com.bridgelabz.fundoonotes.controller;
/**
 * @author shaik shaiksha vali
 * 
 * */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserLoginDetails;
import com.bridgelabz.fundoonotes.dto.UserPasswordUpdateDetails;
import com.bridgelabz.fundoonotes.dto.UserRegistrationDetails;
import com.bridgelabz.fundoonotes.model.UserInformation;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.util.JwtGenerator;

@RestController
@PropertySource("classpath:Message.properties")
public class UserController {

	private UserService userService;

	private JwtGenerator jwtGenerator;
	@Autowired
	private Environment environment;

	@Autowired
	public UserController(UserService iUserService, JwtGenerator iJwtGenerator) {
		this.userService=iUserService;
		this.jwtGenerator=iJwtGenerator;
	}
	//Api for Registration
	/**
	 * 
	 * @param information
	 * @return
	 * @throws Exception
	 */
	@PostMapping("user/Registration")
	//@RequestMapping(path="user/Registration", method=RequestMethod.POST)
	public ResponseEntity<UserResponse> registration(@RequestBody UserRegistrationDetails information) throws Exception {
		System.out.println(information.getEmail());
		boolean reg = userService.register(information);
		if (reg) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserResponse(environment.getProperty("201"), 201, information));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new UserResponse(environment.getProperty("400"), 400, information));
	}
	/**Api for Registration 
	 * @RequestBody-UserLoginDetails
	 */
	@PostMapping("user/Login")
	//@RequestMapping(path="user/Login", method=RequestMethod.POST)
	public ResponseEntity<UserResponse> Login(@RequestBody UserLoginDetails information) {
		UserInformation user = userService.login(information);
		if (user != null) {
			String token = jwtGenerator.jwtToken(user.getUserId());
			System.out.println(token);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"), 202, information));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserResponse(environment.getProperty("401"), 401, information));

	}
	/**
	 * 
	 * @param emailId
	 * @return
	 */
	@PostMapping(value = "forgetPassword/tokenGenerate/{emailId}")
	public ResponseEntity<UserResponse> emailVerify(@PathVariable String emailId) {

		String token = userService.emailVerify(emailId);
		if (token != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(environment.getProperty("202"),202,token));
		}
		return null; 
	}
	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */

	/* API for verifying the token generated for the email */
	@GetMapping("/verify/{token}")
	public ResponseEntity<UserResponse> verify(@PathVariable("token") String token) throws Exception {
		boolean verification = userService.verify(token);
		if (verification) {
			return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(environment.getProperty("200"), 200, token));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new UserResponse(environment.getProperty("502"), 502, token));
	}
	/**
	 * 
	 * @param userUpdate
	 * @return
	 */
	/* API for reset the forget password */
	@PostMapping("user/forgetPassword")
	public ResponseEntity<UserResponse> forgetPassword(@RequestBody UserPasswordUpdateDetails userUpdate,@RequestHeader("token") String token) {
		UserInformation result = userService.forgetPassword(userUpdate,token);
		System.out.println("result -----"+result);
		if (result!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"), 202));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new UserResponse(environment.getProperty("401"), 401));

	}

	/* API for update the user information for the specific token */
	//	@PutMapping("user/update{token}")
	//	public ResponseEntity<UserResponse> update(@PathVariable("token") String token,
	//			@RequestBody UserPasswordUpdateDetails passwordUpdate) {
	//		boolean result = userService.update(passwordUpdate, token);
	//		if (result) {
	//			return ResponseEntity.status(HttpStatus.ACCEPTED)
	//					.body(new UserResponse("password updated successfully", 200, token));
	//		}
	//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse("password doesn't matched", 402, token));
	//
	//	}
	/**
	 * 
	 * @return
	 */

	/* API for retrieving the all the user information */
	@GetMapping("user/allUsers")
	public List<UserInformation> getAllUsers() {
		List<UserInformation> users = userService.getUsers();
		return  users;
	}
	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */

	/* API to get the single user information */
	@GetMapping("user/singleUser")
	public ResponseEntity<UserResponse> singleUser(@RequestHeader("token") String token) throws Exception {
		UserInformation user = userService.getSingleUser(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"), 202, user));
	}

}
