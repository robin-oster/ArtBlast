package dev.ArtBlast.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.ArtBlast.Entities.Authorities;
import dev.ArtBlast.Entities.User;
import dev.ArtBlast.Repository.AuthorityRepository;
import dev.ArtBlast.Repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        List<Authorities> authorities = authorityRepository.findAllByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user, authorities);
    }
}


