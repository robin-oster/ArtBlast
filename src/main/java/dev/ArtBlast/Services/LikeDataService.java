package dev.ArtBlast.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ArtBlast.Entities.Like;
import dev.ArtBlast.Entities.Post;
import dev.ArtBlast.Entities.User;
import dev.ArtBlast.Repository.LikeRepository;
import dev.ArtBlast.Repository.PostRepository;

@Service
public class LikeDataService {

    @Autowired
    private final LikeRepository likesRepository;

    LikeDataService(LikeRepository likesRepository){
        this.likesRepository = likesRepository;
    }

    public Optional<Like> findById(Long id){
        if(likesRepository.existsById(id)){
            return likesRepository.findById(id);
        }
        return null;
    }

    public List<Like> findByPost(Post post){
        return likesRepository.findByPost(post);
    }

    public List<Like> findByUser(User user){
        return likesRepository.findByUser(user);
    }

    public Boolean existsById(Long id){
        return likesRepository.existsById(id);
    }

    public void deleteById(Long id){
        if(likesRepository.existsById(id)){
            likesRepository.deleteById(id);
        }
    }

    public Like save(Like likes){
        if(likes != null){
            return likesRepository.save(likes);
        }
        else return null;
    }
}
