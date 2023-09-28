package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Likes;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findByLikeid(Long id);

    Likes findByTrackLikedAndUserLiked(Track track, User user);

    Long countAllByUserLikedUsername(String username);
}
