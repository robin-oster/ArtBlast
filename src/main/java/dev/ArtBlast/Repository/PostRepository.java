package dev.ArtBlast.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.ArtBlast.Post;
import java.util.List;


public interface PostRepository extends CrudRepository<Post, Long>,
    PagingAndSortingRepository<Post, Long> {

        Post findByIdAndAuthor(Long id, String author);
        Page<Post> findByAuthor(String author, PageRequest pageRequest);
}
