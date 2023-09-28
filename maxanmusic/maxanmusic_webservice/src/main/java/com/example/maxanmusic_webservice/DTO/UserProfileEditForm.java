package com.example.maxanmusic_webservice.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserProfileEditForm {
    private String aboutuser;
    private MultipartFile ppfile;
}
