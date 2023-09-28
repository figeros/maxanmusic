package com.example.maxanmusic_webservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    public Track(String trackName, User artist, String trackDesc, String submitDate) {
        this.trackName = trackName;
        this.artist = artist;
        this.trackDesc = trackDesc;
        this.submitDate = submitDate;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Track_Id")
    private Long trackId;

    @Column(name = "Track_Name")
    private String trackName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Artist_Id")
    private User artist;

    @Column(name = "Track_Cover")
    private String trackDesc = "";

    @Column(name = "Listen_Count")
    private Integer listenCount = 0;

    @Column(name = "Like_Count")
    private Integer likeCount = 0;

    @Column(name = "Date")
    private String submitDate;

    @OneToMany(mappedBy = "commentedTrack",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "trackLiked",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Likes> trackLikes;

    @OneToMany(mappedBy="track",cascade = CascadeType.ALL)
    private Collection<PlaylistTrack> playlistTracks;

}
