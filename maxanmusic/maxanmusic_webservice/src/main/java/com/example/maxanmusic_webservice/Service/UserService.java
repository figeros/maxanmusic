package com.example.maxanmusic_webservice.Service;

import com.example.maxanmusic_webservice.DTO.RegisterForm;
import com.example.maxanmusic_webservice.DTO.TrackShowcaseDTO;
import com.example.maxanmusic_webservice.DTO.UserProfileDTO;
import com.example.maxanmusic_webservice.DTO.UserProfileEditForm;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import com.example.maxanmusic_webservice.Repository.TrackRepository;
import com.example.maxanmusic_webservice.Repository.UserRepository;
import com.example.maxanmusic_webservice.Repository.UserRepositoryPaged;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TrackRepository trackRepository;

    private static S3Client s3;

    @Autowired
    private ModelMapper modelMapper;
    private final UserRepositoryPaged userRepositoryPaged;

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


    public UserProfileDTO userToProfileDTO(User user){
        UserProfileDTO profileDTO = new UserProfileDTO();

        profileDTO = modelMapper.map(user, UserProfileDTO.class);

        return profileDTO;
    }

    public List<UserProfileDTO> userListsToProfileDTOs(List<User> users) {

        List<UserProfileDTO> userProfileDTO = new ArrayList<UserProfileDTO>();

        userProfileDTO = modelMapper.map(users,new TypeToken<List<UserProfileDTO>>(){}.getType());

        return userProfileDTO;
    }


    public List<User> GetUsersByKeyword(String keyword,Integer pageNo){

        Pageable paging = PageRequest.of(pageNo, 8);

        return userRepositoryPaged.findAllByUsernameContainsOrderByFollowercountDesc(keyword,paging);
    }

    public Long GetSearchedUserCountByKeyword(String keyword){
        return userRepository.countAllByUsernameContains(keyword);
    }

    public boolean EditUser(UserProfileEditForm pI, String user, String username) {
        User cookieUser = userRepository.findByUsername(user);
        User userEdited = userRepository.findByUsername(username);

        if(userEdited==null || cookieUser!=userEdited){
            return false;
        }

        userEdited.setAboutuser(pI.getAboutuser());
        if(pI.getPpfile()!=null){
            uploadProfilePhoto(pI.getPpfile(),userEdited.getUserid());
            userEdited.setAvatar(userEdited.getUserid().toString());
        }
        userRepository.save(userEdited);
        return true;
    }

    public Void uploadProfilePhoto(MultipartFile ppfile, Long UserId){
        Region region = Region.EU_CENTRAL_1;

        String bucketName = "maxanmusicbucket";

        s3 = S3Client.builder().region(region).build();


        File file = null;
        try {
            file = File.createTempFile("tempimg",".png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            ppfile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName).key("imgs/pf/"+UserId+".png").acl("public-read").build();

        s3.putObject(request, RequestBody.fromFile(file));
        file.delete();
        return null;
    }

}
