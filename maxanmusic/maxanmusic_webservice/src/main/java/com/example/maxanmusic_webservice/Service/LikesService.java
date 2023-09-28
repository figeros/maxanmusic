package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.Entity.Comment;
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

    public Likes addLike(Long trackid,String username){
        User user = userRepository.findByUsername(username);
        Track track = trackRepository.findByTrackId(trackid);
        Likes newlike = new Likes(user,track);
        Likes savedlike = likesRepository.save(newlike);
        track.setLikeCount(track.getLikeCount()+1);
        trackRepository.save(track);
        return savedlike;
    }

    public Likes getLike(Long trackid,String username){
        User user = userRepository.findByUsername(username);
        Track track = trackRepository.findByTrackId(trackid);
        Likes currentLike = likesRepository.findByTrackLikedAndUserLiked(track,user);
        if(currentLike!=null){
            return currentLike;
        }else return null;
    }


    public Boolean deleteLike(Long trackid,String username){
        User user = userRepository.findByUsername(username);
        Track track = trackRepository.findByTrackId(trackid);
        if(user!=null&&track!=null) {
            Likes currentLike = likesRepository.findByTrackLikedAndUserLiked(track, user);
            if (currentLike != null) {
                likesRepository.delete(currentLike);
                track.setLikeCount(track.getLikeCount() - 1);
                trackRepository.save(track);
                return true;
            }
        }
        return false;
    }
}
