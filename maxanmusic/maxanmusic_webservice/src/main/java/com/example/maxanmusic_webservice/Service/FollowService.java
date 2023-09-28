package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.Entity.Follow;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.FollowRepository;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public Follow follow(String followedUName, String followerUName){

            User follower = userRepository.findByUsername(followerUName);

            User followed = userRepository.findByUsername(followedUName);

            if(follower!=null && followed!=null){
                Follow newfollow = new Follow(follower,followed);
                followRepository.save(newfollow);
                followed.setFollowercount(followed.getFollowercount()+1);
                userRepository.save(followed);
                follower.setFollowedcount(follower.getFollowedcount()+1);
                userRepository.save(follower);
                return newfollow;
            }
            else return null;

    }

    public Boolean isfollowed(String followedUName, String followerUName){
        User follower = userRepository.findByUsername(followerUName);

        User followed = userRepository.findByUsername(followedUName);

        if(follower!=null && followed!=null){
            Follow findfollow = followRepository.findByUserFollowedAndUserFollows(followed,follower);
            if(findfollow!=null) {
                return true;
            }else return false;
        }
        else return false;
    }

    public Boolean unfollow(String followedUName, String followerUName){

        User follower = userRepository.findByUsername(followerUName);

        User followed = userRepository.findByUsername(followedUName);

        if(follower!=null && followed!=null){
            Follow newfollow = followRepository.findByUserFollowedAndUserFollows(followed,follower);
            if(newfollow!=null){
                followRepository.delete(newfollow);
                followed.setFollowercount(followed.getFollowercount()-1);
                userRepository.save(followed);
                follower.setFollowedcount(follower.getFollowedcount()-1);
                userRepository.save(follower);
                return true;
            }else return false;
        }
        else return false;

    }

}
