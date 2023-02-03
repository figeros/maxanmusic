package com.example.maxanmusic_webservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Playlist_Id")
    private Long playlistId;

    @Column(name = "Playlist_Name")
    private String playlistName;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "User_Id")
    private User playlistCreator;

    @OneToMany(mappedBy="playlist")
    private Collection<PlaylistTrack> playlistTracks;
}
