package com.example.maxanmusic_webservice.Controller;

import com.example.maxanmusic_webservice.DTO.CommentUploadForm;
import com.example.maxanmusic_webservice.DTO.TrackEditForm;
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

import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<?> getSearchedTracks(@RequestParam(defaultValue = "") String keyword,
                                               @RequestParam(defaultValue = "0") Integer pageNo){
        if(keyword!=""){
            if((trackService.GetSearchedTracksCountByKeyword(keyword)-(pageNo*8))>0) {
                List<Track> tracks = trackService.GetTracksByKeyword(keyword, pageNo);
                return ResponseEntity.ok(trackService.trackToTrackShowcaseForm(tracks));
            }
            else return ResponseEntity.notFound().build();
        }else return ResponseEntity.badRequest().body("");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrack(@PathVariable Long id)
    {
        Track track = trackService.GetTrackById(id);
        if(track!=null) {
            return ResponseEntity.ok(trackService.trackToSavedTrackDTO(track));
        }
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getTrackComments(@PathVariable Long id,@RequestParam(defaultValue = "0") Integer pageNo)
    {
        if(id!=null){
            if((commentService.GetTrackCommentCountById(id)-(pageNo*7))>0) {
                List<Comment> comments = commentService.GetTrackCommentById(id, pageNo);
                return ResponseEntity.ok(commentService.commentToCommentShowForm(comments));
            }
            else return ResponseEntity.notFound().build();
        }else return ResponseEntity.badRequest().body("");
    }

    @GetMapping("/users-followed")
    public ResponseEntity<?> getFollowedUsersTracks(@CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user,@RequestParam(defaultValue = "0") Integer pageNo){
        if(user!="USER_NOT_FOUND") {
            if((trackService.GetFollowedUsersTracksCount(user)-(pageNo*8))>0) {
                List<Track> tracks = trackService.getFollowedUsersTracks(user,pageNo);
                return ResponseEntity.ok(trackService.trackToTrackShowcaseForm(tracks));
            }
            else return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrack(@CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user,@PathVariable Long id){
        if(user!="USER_NOT_FOUND") {
            if(trackService.DeleteTrack(user,id)){
                return ResponseEntity.ok("Parça Silindi.");
            }
            else return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.badRequest().body("");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> EditTrack(@ModelAttribute TrackEditForm track, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user, @PathVariable Long id){
        if(user!="USER_NOT_FOUND") {
            if(trackService.EditTrack(track,user,id)){
                return ResponseEntity.ok("Parça düzenlendi.");
            }
            else return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.badRequest().body("");
    }
}
