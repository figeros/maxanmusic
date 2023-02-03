package com.example.maxanmusic_webservice.Controller;


import com.example.maxanmusic_webservice.DTO.LoginForm;
import com.example.maxanmusic_webservice.DTO.RegisterForm;
import com.example.maxanmusic_webservice.DTO.UserProfileDTO;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Service.TrackService;
import com.example.maxanmusic_webservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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


    @GetMapping()
    public ResponseEntity<?> getAllUsers()
    {
        return ResponseEntity.ok(userService.GetAllUsers());
    }

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

    @GetMapping("/{id}/tracks")
    public ResponseEntity<?> getAllTracks(@PathVariable Long id)
    {
        return ResponseEntity.ok(userService.GetUsersTracksByUserId(id));
    }
}
