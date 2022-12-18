package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User AddUser(User user) {
        this.userRepository.save(user);
        return user;
    }

    public User GetUser(int userid) {
        return userRepository.findById(userid).get();
    }

    public List<User> GetAllUsers() {

        return userRepository.findAll();
    }

}
