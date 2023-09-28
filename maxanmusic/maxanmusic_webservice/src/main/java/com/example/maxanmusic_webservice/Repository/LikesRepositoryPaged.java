package com.example.maxanmusic_webservice.Repository;

import com.example.maxanmusic_webservice.Entity.Likes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LikesRepositoryPaged extends PagingAndSortingRepository<Likes, Long> {

    List<Likes> findAllByUserLikedUsernameOrderByLikeidDesc(String username, Pageable paged);

}
