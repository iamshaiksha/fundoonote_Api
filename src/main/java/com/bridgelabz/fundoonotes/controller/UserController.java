package com.bridgelabz.fundoonotes.controller;
import java.util.HashMap;
/**
 * @author shaik shaiksha vali
 * 
 * */
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.dto.ForgetPasswordDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDetails;
import com.bridgelabz.fundoonotes.dto.UserRegistrationDetails;
import com.bridgelabz.fundoonotes.model.UserInformation;
//import com.bridgelabz.fundoonotes.model.UserInformationSearch;
import com.bridgelabz.fundoonotes.repository.UserDao;
import com.bridgelabz.fundoonotes.response.Response;
//import com.bridgelabz.fundoonotes.repository.UserDaoSearch;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.AmazonClientService;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.util.JwtGenerator;
@CrossOrigin("/*")
@RestController
@PropertySource("classpath:Message.properties")
public class UserController {
	
	private UserService userService;

	private JwtGenerator jwtGenerator;
	@Autowired
	private Environment environment;
	@Autowired
    private AmazonClientService amazonS3ClientService;


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
	public ResponseEntity<Response> Login(@RequestBody UserLoginDetails information) {
		String token = userService.login(information);
		if (token!= null) {
			
			System.out.println(token);
			Response response = new Response(HttpStatus.OK.value(),"User loggedin successfully", token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return null;
		
	}
	/**
	 * 
	 * @param emailId
	 * @return token
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
	 * @param email
	 * @return
	 */
	/* API for reset the forget password */
	@PostMapping("user/forgetPassword")
	public ResponseEntity<Response> forgetPassword(String email) {
		String result = userService.forgetPassword(email);
		System.out.println("result -----"+result);
		Response response = new Response(HttpStatus.OK.value(), result, "");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
		

	
	//("/resetPassword/{token}")
	@RequestMapping(value = "/resetPassword/{token}", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @Transactional(value = "jpaTransactionManager")
	public ResponseEntity<Response> resetpassword(@Valid@RequestHeader String token,
			@RequestBody ForgetPasswordDto forgetPasswordDto) {
		System.out.println("statting");
		String message = userService.resetpassword(token, forgetPasswordDto);
		System.out.println("#########################"+message);
		Response response = new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<>(response, HttpStatus.OK);
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
	 * @return List<UserInformation>
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
	public ResponseEntity<UserResponse> singleUser(@RequestHeader("token") String token, boolean cacheable) throws Exception {
		UserInformation user = userService.getSingleUser(token,cacheable);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"), 202, user));
	}
	
	@PostMapping(value="/uploadProfile")
    public Map<String, String> uploadProfile(@RequestPart(value = "file") MultipartFile file,@RequestPart("token") String token)
    {
        this.amazonS3ClientService.uploadFileToS3Bucket(file, true,token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + file.getOriginalFilename() + "] uploading request submitted successfully.");

        return response;
    }

    @DeleteMapping(value="/deleteProfile")
    public Map<String, String> deleteProfile(@RequestParam("file_name") String fileName,@RequestPart("token") String token)
    {
        this.amazonS3ClientService.deleteFileFromS3Bucket(fileName,token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + fileName + "] removing request submitted successfully.");

        return response;
    }

}
