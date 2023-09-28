package com.example.maxanmusic_webservice.DTO;

import lombok.Data;

@Data
public class CommentShowForm {
    private String commentorUsername;
    private String commentorAvatar;
    private String comment;
    private String commentDate;
}
