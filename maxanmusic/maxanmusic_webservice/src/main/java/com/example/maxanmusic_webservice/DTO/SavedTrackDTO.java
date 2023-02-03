package com.example.maxanmusic_webservice.DTO;

import lombok.Data;

@Data
public class SavedTrackDTO {
    private Long trackId;
    private String artistUsername;
    private String trackName;
    private String trackDesc;
    private String submitDate;
}
