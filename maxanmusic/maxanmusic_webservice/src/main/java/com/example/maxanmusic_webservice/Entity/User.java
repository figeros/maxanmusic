package com.example.maxanmusic_webservice.Entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class User {
    public User(int userid, String username, String email, String password, String avatar, int followercount, int followedcount, String aboutuser) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.followercount = followercount;
        this.followedcount = followedcount;
        this.aboutuser = aboutuser;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userid")
    private int userid;
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
    private int followercount = 0;
    @Basic
    @Column(name = "followedcount")
    private int followedcount = 0;
    @Basic
    @Column(name = "aboutuser")
    private String aboutuser = "";

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFollowercount() {
        return followercount;
    }

    public void setFollowercount(int followercount) {
        this.followercount = followercount;
    }

    public int getFollowedcount() {
        return followedcount;
    }

    public void setFollowedcount(int followedcount) {
        this.followedcount = followedcount;
    }

    public String getAboutuser() {
        return aboutuser;
    }

    public void setAboutuser(String aboutuser) {
        this.aboutuser = aboutuser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userid == user.userid && followercount == user.followercount && followedcount == user.followedcount && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(avatar, user.avatar) && Objects.equals(aboutuser, user.aboutuser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, username, email, password, avatar, followercount, followedcount, aboutuser);
    }
}
