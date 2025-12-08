package dev.ArtBlast.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.ArtBlast.Post;
import dev.ArtBlast.Repository.PostRepository;

@Service
public class PostDataService {

    @Autowired
    private final PostRepository postRepository;

    PostDataService(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    
    public Post findByIdAndAuthor(Long id, String author){
        System.out.println(postRepository);
        return postRepository.findByIdAndAuthor(id, author);
    }

    public Page<Post> findByAuthor(String author, PageRequest pageRequest){
        return postRepository.findByAuthor(author, pageRequest);
    }

    public Boolean existsById(Long id){
        return postRepository.existsById(id);
    }

    public Boolean existsByIdAndAuthor(Long id, String author){
        return postRepository.existsByIdAndAuthor(id, author);
    }

    public void deleteById(Long id){
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
        }
    }

    public Post save(Post post){
        if(post != null){
            return postRepository.save(post);
        }
        else return null;
    }
}
