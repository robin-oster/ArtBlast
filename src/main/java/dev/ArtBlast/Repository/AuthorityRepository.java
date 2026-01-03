package dev.ArtBlast.Repository;

import org.springframework.data.repository.CrudRepository;

import dev.ArtBlast.Entities.Authorities;
import java.util.List;


public interface AuthorityRepository extends CrudRepository<Authorities, String>{
    
    List<Authorities> findAllByUsername(String username);

}
