package com.poc.aws.services.user.modal.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {

    private String userId;
    private String name;
    private String email;
    private String about;
    private String dob;
    private String mobile;

}
