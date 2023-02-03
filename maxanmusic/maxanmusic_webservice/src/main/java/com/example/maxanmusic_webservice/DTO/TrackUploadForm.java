package com.example.maxanmusic_webservice.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class TrackUploadForm {

    private String trackName;

    private MultipartFile trackAudio;

    private String trackDesc;

    private MultipartFile cover;
}
