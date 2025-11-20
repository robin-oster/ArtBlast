package dev.ArtBlast.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import dev.ArtBlast.Post;
import dev.ArtBlast.PostRepository;

public class PostDataService {

    private final PostRepository postRepository;

    PostDataService(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    
    Post findByIdAndAuthor(Long id, String author){
        return postRepository.findByIdAndAuthor(id, author);
    }

    Page<Post> findByAuthor(String author, PageRequest pageRequest){
        return postRepository.findByAuthor(author, pageRequest);
    }

    public Post save(Post post){
        if(post != null){
            return postRepository.save(post);
        }
        else return null;
    }
}
