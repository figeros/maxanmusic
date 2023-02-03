package com.example.maxanmusic_webservice.Controller;

import com.example.maxanmusic_webservice.DTO.TrackUploadForm;
import com.example.maxanmusic_webservice.Entity.Comment;
import com.example.maxanmusic_webservice.Entity.Likes;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Service.CommentService;
import com.example.maxanmusic_webservice.Service.LikesService;
import com.example.maxanmusic_webservice.Service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;
    private final CommentService commentService;

    private final LikesService likesService;

    @PostMapping("/")
    public ResponseEntity<?> addTrack(@ModelAttribute TrackUploadForm track,@CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user)
    {
        if(user!="USER_NOT_FOUND") {
            Track newTrack = trackService.AddTrack(track, user);
            return ResponseEntity.ok(trackService.trackToTrackUploadForm(newTrack));
        }

        return ResponseEntity.badRequest().body("");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllUsers(@PathVariable Long id)
    {
        return ResponseEntity.ok(trackService.GetTrackById(id));
    }

    @PostMapping("/{id}/comments")
    public Comment addcomment(@PathVariable Long id, @RequestBody Comment comment)
    {
        Comment newComment = commentService.addComment(comment,id);
        return newComment;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long id)
    {
        return ResponseEntity.ok(commentService.getTracksAllComments(id));
    }

    @PostMapping("/{id}/likes")
    public Likes addLikes(@PathVariable Long id, @RequestBody Likes likes)
    {
        Likes newlikes = likesService.addLikes(likes,id);
        return newlikes;
    }

    @DeleteMapping("/{id}/likes")
    public Likes unlike(@PathVariable Long id)
    {
        return likesService.deleteLikes(id);
    }

}
