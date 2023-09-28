package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String Username);
    User findByUserid(Long userid);

    User findByUsernameAndPassword(String Username,String Password);

    Long countAllByUsernameContains(String username);

}
