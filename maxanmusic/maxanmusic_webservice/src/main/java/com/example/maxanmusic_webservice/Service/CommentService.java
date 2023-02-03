package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.Entity.Comment;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.CommentRepository;
import com.example.maxanmusic_webservice.Repository.TrackRepository;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TrackRepository trackRepository;
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public Comment addComment(Comment comment, Long trackId){
        comment.setCommentDate(new Date());
        comment.setCommentedTrack(trackRepository.findByTrackId(trackId));
        commentRepository.save(comment);

        User user = comment.getCommentor();
        user.getComments().add(comment);
        userRepository.save(user);

        Track track = trackRepository.findByTrackId(trackId);
        track.getComments().add(comment);
        trackRepository.save(track);

        return comment;
    }

    public List<Comment> getTracksAllComments(Long id){
        return commentRepository.findAllByCommentedTrack_TrackId(id);
    }

}
