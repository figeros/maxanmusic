package com.example.maxanmusic_webservice.Controller;

import com.example.maxanmusic_webservice.Entity.Follow;
import com.example.maxanmusic_webservice.Service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followed}/users/{follows}")
    public ResponseEntity<?> followUser(@PathVariable Long followed,Long follows)
    {
        Follow follow = followService.follow(follows,followed);
        if(follow!=null){
            ResponseEntity.ok(follow);
        }else ResponseEntity.badRequest().body("Kullanici Zaten Takip Ediliyor");
        return null;
    }

    @DeleteMapping("/{followed}/users/{follows}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long followed,Long follows)
    {
        Follow follow = followService.unfollow(followed,follows);
        if(follow==null){
            ResponseEntity.badRequest().body("Kullanici Zaten Takip Edilmiyor");
        }
        else return ResponseEntity.ok(follow);
        return null;
    }
}
