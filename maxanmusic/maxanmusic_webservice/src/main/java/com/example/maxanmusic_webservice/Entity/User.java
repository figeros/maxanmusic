package com.example.maxanmusic_webservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userid")
    private Long userid;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "avatar")
    private String avatar = "default";

    @Basic
    @Column(name = "followercount")
    private Integer followercount = 0;

    @Basic
    @Column(name = "followedcount")
    private Integer followedcount = 0;

    @Column(name = "aboutuser")
    private String aboutuser = "";

    @OneToMany(mappedBy="artist",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Track> UserTracks;

    @OneToMany(mappedBy = "commentor",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Comment> comments;

    @OneToMany(mappedBy= "userLiked",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Likes> UserLikes;

    @OneToMany(mappedBy="userFollows",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Follow> followedUsers;

    @OneToMany(mappedBy="userFollowed",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Follow> followers;

    @OneToMany(mappedBy="playlistCreator",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Playlist> playlists;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
