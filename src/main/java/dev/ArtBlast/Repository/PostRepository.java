package dev.ArtBlast.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.ArtBlast.Post;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>,
    PagingAndSortingRepository<Post, Long> {

        Post findByIdAndUsername(Long id, String username);
        Page<Post> findByUsername(String username, PageRequest pageRequest);
        Boolean existsByIdAndUsername(Long id, String username);
}
