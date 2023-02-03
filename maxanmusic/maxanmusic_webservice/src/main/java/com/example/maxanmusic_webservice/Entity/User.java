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

    @OneToMany(mappedBy="artist")
    private Collection<Track> UserTracks;

    @OneToMany(mappedBy = "commentor")
    private Collection<Comment> comments;

    @OneToMany(mappedBy= "userLiked")
    private Collection<Likes> UserLikes;

    @OneToMany(mappedBy="userFollows")
    private Collection<Follow> followedUsers;

    @OneToMany(mappedBy="userFollowed")
    private Collection<Follow> followers;

    @OneToMany(mappedBy="user")
    private Collection<Playlist> playlists;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
