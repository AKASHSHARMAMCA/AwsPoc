package com.poc.aws.services.user.repositry;


import com.poc.aws.services.user.modal.dao.UserDetailDynamoDB;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserRepositoryDynamodb extends CrudRepository<UserDetailDynamoDB,String> {

}
