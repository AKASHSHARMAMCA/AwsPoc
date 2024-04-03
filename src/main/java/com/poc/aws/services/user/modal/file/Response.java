package com.poc.aws.services.user.modal.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Response {
    private String message;
    private HttpStatus httpStatus;
}
