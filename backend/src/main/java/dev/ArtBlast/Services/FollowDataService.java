package dev.ArtBlast.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.ArtBlast.Entities.Follow;
import dev.ArtBlast.Entities.User;
import dev.ArtBlast.Repository.FollowRepository;

@Service
public class FollowDataService {

    @Autowired
    FollowRepository followRepository;

    public Page<Follow> findByFollowingUser(User user, PageRequest pageRequest){
        return followRepository.findByFollowingUser(user, pageRequest);
    }

    public Page<Follow> findByFollowedUser(User user, PageRequest pageRequest){
        return followRepository.findByFollowedUser(user, pageRequest);
    }

    public Boolean existsById(Long id){
        return followRepository.existsById(id);
    }

    public Follow save(Follow follow){
        if(follow != null){
            return followRepository.save(follow);
        }
        else return null;
    }
}
