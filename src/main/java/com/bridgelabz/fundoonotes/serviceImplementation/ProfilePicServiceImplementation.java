package com.bridgelabz.fundoonotes.serviceImplementation;

/*
 *  author : Shaik Shaiksha vali
 */

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bridgelabz.fundoonotes.model.ProfilePic;
import com.bridgelabz.fundoonotes.model.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserDao;
import com.bridgelabz.fundoonotes.repository.UserProfileRepository;
import com.bridgelabz.fundoonotes.service.ProfilePicService;
import com.bridgelabz.fundoonotes.util.JwtGenerator;

@Service
public class ProfilePicServiceImplementation implements ProfilePicService {

	private String awsKeyId = System.getenv("AccessKeyId");

	private String acessKey = System.getenv("SecretAccessKey");

	
	
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(awsKeyId, acessKey);
		amazonS3 = new AmazonS3Client(credentials);
	}
	
	private AmazonS3 amazonS3;
	@Autowired
	private UserProfileRepository profileRepository;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private UserDao userRepo;

	private String bucketName = System.getenv("BucketName");
/*Method for uploading the profilepic for a user in s3 bucket*/
	@Transactional
	@Override
	public ProfilePic uploadFileTos3Bucket(MultipartFile file, String fileName,  String token) {
		try {
			Long id = (Long) generate.parseJWT(token);
			UserInformation user = userRepo.findUserById(id);
			if (user != null) {
				ProfilePic profile = new ProfilePic(fileName, user);
				ObjectMetadata data = new ObjectMetadata();
			//	data.setContentType(contentType);
				//data.setContentLength(file.getSize());
				System.out.println("#############");

				amazonS3.putObject(bucketName,fileName,file.getInputStream() ,data);
				System.out.println("%%%%%%%%%%%%");
				profileRepository.save(profile);
			}
		} catch (SdkClientException | IOException e) {

			throw new RuntimeException("can't upload the profile ");

		}

		return null;
	}
	/*Method for updating the profilepic for a user in s3 bucket*/
	@Transactional
	@Override
	public ProfilePic updateProfile(MultipartFile file, String originalFilename, String contentType, String token) {

		try {
			Long userId = generate.parseJWT(token);
			UserInformation user = userRepo.findUserById(userId);
			ProfilePic profile = profileRepository.findUserById(userId);
			if (user != null && profile != null) {
				deleteobjectFromS3Bucket(profile.getProfilePicName());
				profileRepository.delete(profile);
				ObjectMetadata objectMetadata = new ObjectMetadata();
//				objectMetadata.setContentType(contentType);
//				objectMetadata.setContentLength(file.getSize());

				amazonS3.putObject(bucketName, originalFilename, file.getInputStream(), objectMetadata);
				profileRepository.save(profile);
				return profile;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*Method for deleting the profilepic for a user in s3 bucket*/
	@Transactional
	@Override
	public void deleteobjectFromS3Bucket(String key) {
		try {
			amazonS3.deleteObject(bucketName, key);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}

}