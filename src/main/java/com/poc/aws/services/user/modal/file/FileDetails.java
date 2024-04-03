package com.poc.aws.services.user.modal.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDetails {

    private String fileData;
    private String fileName;
    private String fileType;
}
