package com.poc.aws.services.user.controller;

import com.poc.aws.services.user.modal.dao.UserDetailDynamoDB;
import com.poc.aws.services.user.modal.file.Response;
import com.poc.aws.services.user.services.UserServicesDynamoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/userServices")
public class UserDynamoDBController {

    @Autowired
    UserServicesDynamoDB userServicesDynamoDB;

    @PostMapping("/user")
    public ResponseEntity<UserDetailDynamoDB> createUser(@RequestBody UserDetailDynamoDB userDetail){
        return ResponseEntity.status(HttpStatus.CREATED).body(userServicesDynamoDB.saveUser(userDetail));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDetailDynamoDB>> getAllUsers(){
        return ResponseEntity.ok(userServicesDynamoDB.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetailDynamoDB> getUserById(@PathVariable String id){
        return ResponseEntity.ok(userServicesDynamoDB.getUser(id));
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<Response> saveResponse(@RequestParam("file") MultipartFile multipartFile){
        return new ResponseEntity<>(userServicesDynamoDB.sendFile(multipartFile), HttpStatus.CREATED);
    }
}
