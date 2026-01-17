package dev.ArtBlast.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.ArtBlast.Entities.Like;
import dev.ArtBlast.Entities.Post;
import dev.ArtBlast.Entities.User;

public interface LikeRepository extends CrudRepository<Like, Long>{
    List<Like> findByPost(Post post);
    List<Like> findByUser(User user);
}
