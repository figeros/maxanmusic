package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.DTO.RegisterForm;
import com.example.maxanmusic_webservice.DTO.UserProfileDTO;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.TrackRepository;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TrackRepository trackRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Boolean UserExists(String username){
       if(userRepository.findByUsername(username)!=null)
           return true;
       else return false;
    }

    public User AddUser(RegisterForm userform) {

        if(!UserExists(userform.getUsername())){
            User user = new User(userform.getUsername(),userform.getEmail(),userform.getPassword());
            userRepository.save(user);
            return user;
        }
        else return null;
    }

    public Boolean CheckUser(String username,String password){

        User user = userRepository.findByUsernameAndPassword(username,password);

        if(user!=null) {
            return true;
        }
        else return false;
    }

    public Boolean CheckUser(String username){

        User user = userRepository.findByUsername(username);

        if(user!=null) {
            return true;
        }
        else return false;
    }

    public User GetUserByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    public List<Track> GetUsersTracksByUserId(Long id){
        return trackRepository.findAllByArtist_Userid(id);
    }

    public UserProfileDTO userToProfileDTO(User user){
        UserProfileDTO profileDTO = new UserProfileDTO();

        profileDTO = modelMapper.map(user, UserProfileDTO.class);

        return profileDTO;
    }
}
