package com.example.maxanmusic_webservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    public Comment(User commentor, Track commentedTrack, String comment, String commentDate) {
        this.commentor = commentor;
        this.commentedTrack = commentedTrack;
        this.comment = comment;
        this.commentDate = commentDate;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Comment_Id")
    private Long commentId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id")
    private User commentor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Track_Id")
    private Track commentedTrack;

    @Column(name = "Content")
    private String comment;

    @Column(name = "Comment_Date")
    private String commentDate;
}
