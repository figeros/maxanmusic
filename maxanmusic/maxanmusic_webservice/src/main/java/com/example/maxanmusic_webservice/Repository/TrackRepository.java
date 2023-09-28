package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Follow;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    Track findByTrackId(Long id);

    Long countAllByArtistUsernameOrderByTrackId(String username);

    Long countAllByTrackNameContains(String username);

    Long countAllByArtistIn(List<User> users);
}

