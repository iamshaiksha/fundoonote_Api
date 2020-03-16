package com.bridgelabz.fundoonotes.service;

/*
 *  author : Shaiksha
 */

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.ProfilePic;

public interface ProfilePicService {
	/**
	 * 
	 * @param file
	 * @param fileName
	 * @param token
	 * @return
	 */

	public ProfilePic uploadFileTos3Bucket(MultipartFile file, String fileName, String token);
	/**
	 * 
	 * @param file
	 * @param originalFilename
	 * @param contentType
	 * @param token
	 * @return
	 */

	public ProfilePic updateProfile(MultipartFile file, String originalFilename, String contentType, String token);
	/**
	 * 
	 * @param key
	 */
	public void deleteobjectFromS3Bucket(String key);

}