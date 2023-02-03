package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    Track findByTrackId(Long id);

    List<Track> findAllByArtist_Userid(Long id);
}
