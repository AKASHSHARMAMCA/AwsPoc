package com.poc.aws.services.user.services;



import com.poc.aws.services.user.modal.dao.UserDetailDynamoDB;
import com.poc.aws.services.user.modal.file.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserServicesDynamoDB {
    UserDetailDynamoDB saveUser(UserDetailDynamoDB userDetail);

    UserDetailDynamoDB getUser(String userId);

    List<UserDetailDynamoDB> getAllUsers();

    Response sendFile(MultipartFile file);
}
