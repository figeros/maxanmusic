package com.example.maxanmusic_webservice.DTO;

import lombok.Data;

@Data
public class TrackShowcaseDTO {
    private Long trackId;
    private String artistUsername;
    private String trackName;
    private String trackDesc;
    private Integer listenCount;
    private Integer likeCount;
}
