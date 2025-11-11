package dev.ArtBlast;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface PostRepository extends CrudRepository<Post, Long>,
    PagingAndSortingRepository<Post, Long> {

        Post findByIdAndAuthor(Long id, String author);
        Page<Post> findByAuthor(String author);
}
