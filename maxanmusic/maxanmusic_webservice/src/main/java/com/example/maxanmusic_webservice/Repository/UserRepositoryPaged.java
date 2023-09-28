package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepositoryPaged extends PagingAndSortingRepository<User, Long> {

    List<User> findAllByUsernameContainsOrderByFollowercountDesc(String username, Pageable pageable);

}
