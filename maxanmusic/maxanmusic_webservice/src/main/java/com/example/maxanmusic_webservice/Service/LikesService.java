package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.Entity.Likes;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.LikesRepository;
import com.example.maxanmusic_webservice.Repository.TrackRepository;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final TrackRepository trackRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;

    public Likes addLikes(Likes likes,Long id){
        likes.setTrackLiked(trackRepository.findByTrackId(id));
        likesRepository.save(likes);

        User user = userRepository.findByUserid(likes.getUserLiked().getUserid());
        user.getUserLikes().add(likes);
        userRepository.save(user);

        Track track = trackRepository.findByTrackId(id);
        track.getTrackLikes().add(likes);
        track.setLikeCount(track.getLikeCount()+1);
        trackRepository.save(track);
        return likes;
    }

    public Likes deleteLikes(Long id){
        Likes likes = likesRepository.findByLikeid(id);

        User user = likesRepository.findByLikeid(id).getUserLiked();
        user.getUserLikes().remove(likes);
        userRepository.save(user);

        Track track = likesRepository.findByLikeid(id).getTrackLiked();
        track.getTrackLikes().remove(likes);
        track.setLikeCount(track.getLikeCount()-1);
        trackRepository.save(track);

        likesRepository.delete(likes);

        return likes;
    }
}
