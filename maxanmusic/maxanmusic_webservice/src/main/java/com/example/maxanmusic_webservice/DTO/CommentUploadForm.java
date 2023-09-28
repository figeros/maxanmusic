package com.example.maxanmusic_webservice.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommentUploadForm {

    private String comment;

    private Long commentedTrack;
}
