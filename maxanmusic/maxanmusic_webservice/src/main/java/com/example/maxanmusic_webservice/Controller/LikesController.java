package com.example.maxanmusic_webservice.Controller;

import com.example.maxanmusic_webservice.DTO.CommentUploadForm;
import com.example.maxanmusic_webservice.Entity.Comment;
import com.example.maxanmusic_webservice.Entity.Likes;
import com.example.maxanmusic_webservice.Repository.LikesRepository;
import com.example.maxanmusic_webservice.Service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("")
    public ResponseEntity<?> addLike(@RequestBody Long track, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user)
    {
        if(user!="USER_NOT_FOUND") {
            likesService.addLike(track, user);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.badRequest().body("");
    }

    @GetMapping("")
    public ResponseEntity<?> showlike(@RequestParam Long trackId, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user)
    {
        if(user!="USER_NOT_FOUND") {
            Likes like = likesService.getLike(trackId,user);
            if(like!=null) {
                return ResponseEntity.ok("");
            }else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("")
    public ResponseEntity<?> unlike(@RequestParam Long trackId, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user)
    {
        //&&trackId!=null 'u da kontrol et
        if(user!="USER_NOT_FOUND") {
            if(likesService.deleteLike(trackId,user)) {
                return ResponseEntity.ok("");
            }else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body("");
    }
}
