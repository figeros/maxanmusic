package com.example.maxanmusic_webservice.Controller;


import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public User addUser(@RequestBody User user)
    {
        return userService.AddUser(user);
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers()
    {
        return ResponseEntity.ok(userService.GetAllUsers());
    }
}
