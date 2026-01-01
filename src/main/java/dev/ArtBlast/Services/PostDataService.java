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
    
    public Post findByIdAndUsername(Long id, String username){
        System.out.println(postRepository);
        return postRepository.findByIdAndUsername(id, username);
    }

    public Page<Post> findByUsername(String username, PageRequest pageRequest){
        return postRepository.findByUsername(username, pageRequest);
    }

    public Boolean existsById(Long id){
        return postRepository.existsById(id);
    }

    public Boolean existsByIdAndUsername(Long id, String username){
        return postRepository.existsByIdAndUsername(id, username);
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
