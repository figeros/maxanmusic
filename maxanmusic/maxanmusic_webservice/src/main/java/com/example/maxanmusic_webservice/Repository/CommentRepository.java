package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByCommentId(Long id);

    List<Comment> findAllByCommentedTrack_TrackId(Long id);

    Long countAllByCommentedTrack_TrackId(Long id);
}
