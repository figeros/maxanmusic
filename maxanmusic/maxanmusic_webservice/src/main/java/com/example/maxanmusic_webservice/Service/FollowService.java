package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.Entity.Follow;
import com.example.maxanmusic_webservice.Repository.FollowRepository;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public Follow follow(Long followerid, Long followedid){

        if(followRepository.findByUserFollowed_UseridAndUserFollows_Userid(followedid,followerid) == null)
        {

            Follow newfollow = new Follow();

            newfollow.setUserFollows(userRepository.findByUserid(followerid));
            newfollow.setUserFollowed(userRepository.findByUserid(followedid));
            followRepository.save(newfollow);
            return newfollow;
        }
        else return null;
    }

    public Follow unfollow(Long followedid,Long followerid) {
        Follow follow = followRepository.findByUserFollowed_UseridAndUserFollows_Userid(followedid, followerid);
        if (follow != null)
        {
            followRepository.delete(follow);
            return follow;
        }
        else return null;
    }

}
