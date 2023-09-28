package com.example.maxanmusic_webservice.Controller;

import com.example.maxanmusic_webservice.DTO.CommentUploadForm;
import com.example.maxanmusic_webservice.Entity.Comment;
import com.example.maxanmusic_webservice.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> addComment(@RequestBody CommentUploadForm comment, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user)
    {
        if(user!="USER_NOT_FOUND") {
            Comment newComment = commentService.AddCommentToTrack(comment, user);
            return ResponseEntity.ok(commentService.savedCommentToCommentShowForm(newComment));
        }

        return ResponseEntity.badRequest().body("");
    }

}
