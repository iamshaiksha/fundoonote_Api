package com.bridgelabz.fundoonotes.controller;

/**
 *  author :  shaik shaiksha vali
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.ProfilePic;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.ProfilePicService;

@RestController
public class ProfileController {

	@Autowired
	private ProfilePicService service;
	/**
	 * 
	 * @param file
	 * @param fileName
	 * @param token
	 * 
	 */
	/* API for add the profile */
	@PostMapping("profile/add")
	public ResponseEntity<UserResponse> addProfile(@RequestBody MultipartFile file, String fileName,
			@RequestHeader("token") String token) {
		ProfilePic add = service.uploadFileTos3Bucket(file, fileName, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse("profilePic added", 200, add));
	}
	/**
	 * 
	 * @param file
	 * @param fileName
	 * @param contentType
	 * @param token
	 * 
	 */

	/* API for update the profilepic */
	@PutMapping("profile/update")
	public ResponseEntity<UserResponse> update(@RequestBody MultipartFile file, String fileName, String contentType,
			@RequestHeader("token") String token) {
		ProfilePic update = service.updateProfile(file, fileName, contentType, token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse("updated successfully", 200, update));
	}
/**
 * 
 * @param key
 * 
 */
	/* API for delete the profile */
	@DeleteMapping("profile)/delete")
	public ResponseEntity<UserResponse> deleteProfile(@RequestBody String key) {
		service.deleteobjectFromS3Bucket(key);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse("deleted successfully", 200));
	}

}