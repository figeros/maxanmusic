package com.example.maxanmusic_webservice.DTO;

import lombok.Data;

@Data
public class UserProfileDTO {
    private String username;
    private String avatar;
    private Integer followercount;
    private Integer followedcount;
    private String aboutuser;
}
