package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Comment;
import com.example.maxanmusic_webservice.Entity.Track;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepositoryPaged extends PagingAndSortingRepository<Comment, Long> {

    List<Comment> findAllByCommentedTrack_TrackIdOrderByCommentIdDesc(Long id, Pageable pageable);
}
