package com.example.maxanmusic_webservice.Controller;

import com.example.maxanmusic_webservice.Service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor    
public class FollowController {

    private final FollowService followService;

    @PostMapping("")
    public ResponseEntity<?> followUser(@RequestBody String followedUser, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String followingUser)
    {

        if(followingUser!="USER_NOT_FOUND") {
            followService.follow(followedUser,followingUser);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.badRequest().body("");
    }

    @GetMapping("")
    public ResponseEntity<?> getfollow(@RequestParam String followedUser, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String followingUser)
    {
        if(followingUser!="USER_NOT_FOUND") {
            if(followService.isfollowed(followedUser,followingUser)) {
                return ResponseEntity.ok("");
            }
            else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("")
    public ResponseEntity<?> unfollowUser(@RequestParam String followedUser, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String followingUser)
    {

        if(followingUser!="USER_NOT_FOUND") {
            if(followService.unfollow(followedUser,followingUser)) {
                return ResponseEntity.ok("");
            }
            else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body("");
    }
}
