package dev.ArtBlast.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ArtBlast.Entities.User;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    User findByUsername(String username);
    Page<User> findByTrusted(Boolean trusted, PageRequest pageRequest);
}
