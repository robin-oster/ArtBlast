package dev.ArtBlast.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.ArtBlast.Entities.Follow;
import dev.ArtBlast.Entities.User;

@Repository
public interface FollowRepository extends CrudRepository<Follow, Long>,
    PagingAndSortingRepository<Follow, Long>{
    
    Page<Follow> findByFollowingUser(User user, PageRequest pageRequest);
    Page<Follow> findByFollowedUser(User user, PageRequest pageRequest);

}
