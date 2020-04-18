package com.bridgelabz.fundoonotes.service;
import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.UserInformation;

public interface AmazonClientService {
	URL uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,String token);

	void deleteFileFromS3Bucket(String fileName,String token);
}