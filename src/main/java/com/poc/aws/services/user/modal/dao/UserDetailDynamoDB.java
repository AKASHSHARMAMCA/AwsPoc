package com.poc.aws.services.user.modal.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@DynamoDBTable(tableName = "userd")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class UserDetailDynamoDB {
    @DynamoDBHashKey(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "email")
    private String email;
    @DynamoDBAttribute(attributeName = "about")
    private String about;
    @DynamoDBAttribute(attributeName = "dob")
    private String dob;
    @DynamoDBAttribute(attributeName = "mobile")
    private String mobile;
    @DynamoDBAttribute(attributeName = "profilePic")
    private String profilePic;
}
