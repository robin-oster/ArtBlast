package dev.ArtBlast.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.ArtBlast.Entities.Like;
import dev.ArtBlast.Entities.Post;
import dev.ArtBlast.Entities.User;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long>, 
    PagingAndSortingRepository<Like, Long>{

    Page<Like> findByPost(Post post, PageRequest pageRequest);
    Page<Like> findByUser(User user, PageRequest pageRequest);
    Like findByUserAndPost(User user, Post post);
    Boolean existsByUserAndPost(User user, Post post);
}
