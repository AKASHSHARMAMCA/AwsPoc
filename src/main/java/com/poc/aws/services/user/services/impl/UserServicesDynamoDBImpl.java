package com.poc.aws.services.user.services.impl;



import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.aws.services.user.exceptions.UserNotFoundException;
import com.poc.aws.services.user.modal.dao.UserDetailDynamoDB;
import com.poc.aws.services.user.modal.file.FileDetails;
import com.poc.aws.services.user.modal.file.Response;
import com.poc.aws.services.user.repositry.UserRepositoryDynamodb;
import com.poc.aws.services.user.services.UserServicesDynamoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class UserServicesDynamoDBImpl implements UserServicesDynamoDB {

    private static final Logger logger = LoggerFactory.getLogger(UserServicesDynamoDBImpl.class);
    private static final String PROFILE_PIC = "_profilePic.jpg";
    private static final String IMAGE_JPEG = "image/jpeg";
    private static final String S3_URL = "https://buckerforawspoc.s3.ap-south-1.amazonaws.com/";

    @Autowired
    UserRepositoryDynamodb userRepositoryDynamodb;
    @Autowired
    private AmazonSQS sqsClient;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    @Override
    public UserDetailDynamoDB saveUser(UserDetailDynamoDB userDetail) {
        String profilePicUrlToS3 = null;
        try {
            logger.info("upload profile picture to s3 for this user ID :: {} ", userDetail.getUserId());
            profilePicUrlToS3 = getUrlToS3(userDetail);
        }catch (IOException e){
            e.printStackTrace();
            logger.error("profile Picture not upload to s3 for this user ID :: {} ", userDetail.getUserId());
        }
        userDetail.setProfilePic(profilePicUrlToS3);
        return userRepositoryDynamodb.save(userDetail);
    }

    private String getUrlToS3(UserDetailDynamoDB userDetail) throws JsonProcessingException {
        FileDetails fileDetails;
        String fileString;
        fileDetails = FileDetails.builder().
                fileData(userDetail.getProfilePic()).
                fileName(userDetail.getName() + PROFILE_PIC).
                fileType(IMAGE_JPEG).
                build();

        fileString = new ObjectMapper().writeValueAsString(fileDetails);

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(fileString);

        String sendMessageResult =  sqsClient.sendMessage(sendMessageRequest).getMessageId();
        String profilePicUrlToS3 = S3_URL+ userDetail.getName()+ PROFILE_PIC;
        if(!sendMessageResult.isEmpty()){
            logger.info("profile picture upload to s3 is complete and save in DynamoDB with this messageID {} for this user ID :: {} " , sendMessageResult,userDetail.getUserId());
        }
        return profilePicUrlToS3;
    }

    @Override
    public UserDetailDynamoDB getUser(String userId) {
        return userRepositoryDynamodb.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public List<UserDetailDynamoDB> getAllUsers() {
        return (List<UserDetailDynamoDB>) userRepositoryDynamodb.findAll();
    }
    @Override
    public Response sendFile(MultipartFile file) {
        FileDetails fileDetails;
        String fileString;
        try {
            fileDetails = FileDetails.builder().
                    fileData(Base64.getEncoder().encodeToString(file.getInputStream().readAllBytes())).
                    fileName(file.getOriginalFilename()).
                    fileType(file.getContentType()).
                    build();
            fileString = new ObjectMapper().writeValueAsString(fileDetails);
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(fileString);

            sqsClient.sendMessage(sendMessageRequest);
            return new Response("File sent with request id: ", HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new Response("not going on s3",HttpStatus.NOT_FOUND);
        }
    }
}
