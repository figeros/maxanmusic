package com.example.maxanmusic_webservice.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SavedTrackDTO {
    private Long trackId;
    private String artistUsername;
    private String trackName;
    private String trackDesc;
    private String submitDate;
    private Integer listenCount;
    private Integer likeCount;
}
