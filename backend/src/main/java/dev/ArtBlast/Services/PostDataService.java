package dev.ArtBlast.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.ArtBlast.Entities.Post;
import dev.ArtBlast.Repository.PostRepository;

@Service
public class PostDataService {

    @Autowired
    private final PostRepository postRepository;

    PostDataService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post findById(Long id){
        if(postRepository.existsById(id)){
            return postRepository.findById(id).get();
        }
        return null;
    }
    
    public Post findByIdAndUsername(Long id, String username){
        return postRepository.findByIdAndUsername(id, username);
    }

    public Page<Post> findByUsername(String username, PageRequest pageRequest){
        return postRepository.findByUsernameOrderByDateTimeDesc(username, pageRequest);
    }

    public Page<Post> findByUsernameAndTimesReportedLessThan(String username, Long value, PageRequest pageRequest){
        return postRepository.
            findByUsernameAndTimesReportedLessThanOrderByDateTimeDesc(username, value, pageRequest);
    }

    public Page<Post> findByPending(Boolean pending, PageRequest pageRequest){
        return postRepository.findByPendingOrderByDateTimeDesc(pending, pageRequest);
    }

    public Page<Post> findTimesReportedLessThan(Long value, PageRequest pageRequest){
        return postRepository.findTimesReportedLessThan(value, pageRequest);
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
