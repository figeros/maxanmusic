package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepositoryPaged extends PagingAndSortingRepository<Track, Long> {

    List<Track> findAllByArtistUsernameOrderByTrackIdDesc(String username, Pageable pageable);

    List<Track> findAllByTrackNameContainsOrderByTrackIdDesc(String trackname, Pageable pageable);

    List<Track> findAllByArtistInOrderByTrackIdDesc(List<User> users,Pageable pageable);

}
