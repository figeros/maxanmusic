package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByUserFollowed_Userid(Long id);

    Follow findByUserFollowed_UseridAndUserFollows_Userid(Long idd,Long ids);
}
