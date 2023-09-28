package com.example.maxanmusic_webservice.Controller;


import com.example.maxanmusic_webservice.DTO.*;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Service.TrackService;
import com.example.maxanmusic_webservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TrackService trackService;


    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody RegisterForm user)
    {
        User addeduser = userService.AddUser(user);

        if(addeduser!=null){
            return ResponseEntity.ok(addeduser);
        }

        return ResponseEntity.badRequest().body("Bu Kullanıcı ismi zaten kullanılıyor");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm user, HttpServletResponse response) {
        if (userService.CheckUser(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok("Giris Basarili!");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/current")
    public ResponseEntity<?> profilePage(@CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user) {

        if(user!="USER_NOT_FOUND"){
            System.out.println(userService.GetUserByUsername(user));
            return ResponseEntity.ok(userService.GetUserByUsername(user));
        }
        return ResponseEntity.badRequest().body("böyle bir kullanıcı yok");
    }


    /*@GetMapping()
    public ResponseEntity<?> getAllUsers()
    {
        return ResponseEntity.ok(userService.GetAllUsers());
    }*/

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username)
    {
        System.out.println(username);
        if(userService.CheckUser(username)){
            User user = userService.GetUserByUsername((username));
            return ResponseEntity.ok(userService.userToProfileDTO(user));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{username}/tracks")
    public ResponseEntity<?> getUsersAllTracks(@PathVariable String username,
                                          @RequestParam(defaultValue = "0") Integer pageNo)
    {
        if((trackService.GetUsersTracksCountByUserName(username)-(pageNo*6))>0) {
            List<Track> tracks = trackService.GetUsersTracksByUserName(username, pageNo);
            return ResponseEntity.ok(trackService.trackToTrackShowcaseForm(tracks));
        }
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<?> getSearchedUsers(@RequestParam(defaultValue = "") String keyword,
                                          @RequestParam(defaultValue = "0") Integer pageNo)
    {
        if(keyword!="") {
            if ((userService.GetSearchedUserCountByKeyword(keyword) - (pageNo * 8)) > 0) {
                List<User> users = userService.GetUsersByKeyword(keyword, pageNo);
                return ResponseEntity.ok(userService.userListsToProfileDTOs(users));
            } else return ResponseEntity.notFound().build();
        }else return ResponseEntity.badRequest().body("");
    }

    @GetMapping("/liked-tracks")
    public ResponseEntity<?> getLikedTrack(@CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user,@RequestParam(defaultValue = "0") Integer pageNo)
    {
        if(user!="USER_NOT_FOUND") {
            if((trackService.GetLikedTracksCount(user)-(pageNo*8))>0) {
                List<Track> tracks = trackService.getLikedTracks(user,pageNo);
                return ResponseEntity.ok(trackService.trackToTrackShowcaseForm(tracks));
            }
            else return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.badRequest().body("");
    }

    @PatchMapping("/{username}")
    public ResponseEntity<?> EditTrack(@ModelAttribute UserProfileEditForm pI, @CookieValue(name = "maxanmusicuser", defaultValue = "USER_NOT_FOUND") String user, @PathVariable String username){
        if(user!="USER_NOT_FOUND") {
            if(userService.EditUser(pI,user,username)){
                return ResponseEntity.ok("Profil düzenlendi.");
            }
            else return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.badRequest().body("");
    }
}
