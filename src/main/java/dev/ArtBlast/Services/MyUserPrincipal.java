package dev.ArtBlast.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.ArtBlast.Entities.User;

public class MyUserPrincipal implements UserDetails{
    private User user;

    public MyUserPrincipal(){}

    public MyUserPrincipal(User user){
        this.user = user;
    }

    public Long getId(){
        return user.getId();
    }

    public String getUsername(){
        return user.getUsername();
    }

    public String getPassword(){
        return user.getPassword();
    }

    public Boolean getEnabled(){
        return user.getEnabled();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public String getAvatar(){
        return user.getAvatar();
    }

    public String getBio(){
        return user.getBio();
    }

    public Collection<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(new SimpleGrantedAuthority(user.getAuthority()));
        Collection<SimpleGrantedAuthority> newAuthorities = updatedAuthorities;
        return newAuthorities;
    }
}