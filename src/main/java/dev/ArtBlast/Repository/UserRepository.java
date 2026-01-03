package dev.ArtBlast.Repository;

import org.springframework.data.repository.CrudRepository;

import dev.ArtBlast.Entities.User;
import java.util.List;


public interface UserRepository extends CrudRepository<User, Long>{

    User findByIdAndUsername(Long id, String username);
    User findByUsername(String username);
    User findByEmail(String email);
    Boolean existsByIdAndUsername(Long id, String username);
    
}
