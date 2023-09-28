package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Follow;
import com.example.maxanmusic_webservice.Entity.Track;
import com.example.maxanmusic_webservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByUserFollowed_UsernameAndUserFollows_Username(String fllwed,String fllws);
    Follow findByUserFollowedAndUserFollows(User fllwd, User fllws);

    List<Follow> findAllByUserFollowsUsername(String username);
}
