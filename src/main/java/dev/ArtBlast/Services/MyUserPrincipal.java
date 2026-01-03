package dev.ArtBlast.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.ArtBlast.Entities.Authorities;
import dev.ArtBlast.Entities.User;

public class MyUserPrincipal implements UserDetails{
    private User user;
    private List<Authorities> authorities;

    public MyUserPrincipal(User user, List<Authorities> authorities){
        this.user = user;
        this.authorities = authorities;
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
        for(Authorities i : authorities){
            updatedAuthorities.add(new SimpleGrantedAuthority(i.getAuthority()));
        }
        Collection<SimpleGrantedAuthority> newAuthorities = updatedAuthorities;
        return newAuthorities;
    }
}