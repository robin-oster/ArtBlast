package dev.ArtBlast.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<Like> findByPost(Post post, PageRequest pageRequest){
        return likesRepository.findByPost(post, pageRequest);
    }

    public Page<Like> findByUser(User user, PageRequest pageRequest){
        return likesRepository.findByUser(user, pageRequest);
    }

    public Boolean existsByUserAndPost(User user, Post post){
        return likesRepository.existsByUserAndPost(user, post);
    }

    public Like findByUserAndPost(User user, Post post){
        return likesRepository.findByUserAndPost(user, post);
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
