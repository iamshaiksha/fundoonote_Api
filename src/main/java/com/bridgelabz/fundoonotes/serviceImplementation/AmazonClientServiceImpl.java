//package com.bridgelabz.fundoonotes.serviceImplementation;
//
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import org.springframework.util.StringUtils;
//
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.bridgelabz.fundoonotes.model.UserInformation;
//import com.bridgelabz.fundoonotes.repository.UserDao;
//import com.bridgelabz.fundoonotes.service.AmazonClientService;
//import com.bridgelabz.fundoonotes.util.JwtGenerator;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
//import com.amazonaws.regions.Region;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URL;
//@Service
//public class AmazonClientServiceImpl implements AmazonClientService{
//	
//	 private String awsS3AudioBucket;
//	    
//	    private AmazonS3 amazonS3;
//
//		@Autowired
//		private UserDao userRepository;
//		
//		@Autowired
//		private JwtGenerator generate;
//		
//		@Autowired
//		 private Environment env; 
//		
//	    private static final Logger logger = LoggerFactory.getLogger(AmazonClientServiceImpl.class);
//
//	    @Autowired
//	    public AmazonClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket) 
//	    {
//	        this.amazonS3 = AmazonS3ClientBuilder.standard()
//	                .withCredentials(awsCredentialsProvider)
//	                .withRegion(awsRegion.getName()).build();
//	        this.awsS3AudioBucket = awsS3AudioBucket;
//	    }
//
//	    @Async
//	    public URL uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,String token) 
//	    {
//	    	long id = (Long) generate.parseJWT(token);
//	    	
//	    		UserInformation user = userRepository.findUserById(id);
//	    	
//	        String fileName =StringUtils.cleanPath(multipartFile.getOriginalFilename());
//	        String fileType=multipartFile.getContentType();
//	        
//	        user.setProfile(fileName);
//
//	        user.setFileType(fileType);
////	        try {
//////				user.setData(multipartFile.getBytes());
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////	        myAmazonS3Client.putObject(new PutObjectRequest('some-grails-bucket',  'somePath/someKey.jpg', new    File('/Users/ben/Desktop/photo.jpg')).withCannedAcl(CannedAccessControlList.PublicRead))
//	        try {
//	            //creating the file in the server (temporarily)
//	            File file = new File(fileName);
//	            FileOutputStream fos = new FileOutputStream(file);
//	            fos.write(multipartFile.getBytes());
//	            fos.close();
//	            
//	            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);
//
//	            if (enablePublicReadAccess) {
//	                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
//	            }
//	            this.amazonS3.putObject(putObjectRequest);
////	            s3Client.getUrl("your-bucket", fileName).toString();
//	            //removing the file created in the server
//	            AmazonS3Client s3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
//	                    .withRegion("ap-south-1")
//	                    .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
//	                    .withPathStyleAccessEnabled(true)
//	                    .build();
//	            URL s3Url = s3Client.getUrl("iamshaiksha", fileName);
//	            System.out.println("S3 url is " + s3Url.toExternalForm());
//	            System.out.println("###url"+s3Url);
//	            
//	            file.delete();
//	             userRepository.save(user);
//	            return s3Url;
//	        } catch (IOException | AmazonServiceException ex) {
//	            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
//	        }
//			return null;
//	    }
//
//	    @Async
//	    public void deleteFileFromS3Bucket(String fileName,String token) 
//	    {
//	    	long id = (Long) generate.parseJWT(token);
//	    	
//			UserInformation user = userRepository.findUserById(id);
//				
//
//			 user.setProfile("null");
//	        try {
//	            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
//	        } catch (AmazonServiceException ex) {
//	            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
//	        }
//	        userRepository.save(user);
//	    }
//
//}