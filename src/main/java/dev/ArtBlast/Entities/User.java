package dev.ArtBlast.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Bool;

@Entity
@Table(name = "USERS")
@Getter
@Setter
public class User {

    public User(){}

    public User(Long id, String username, String password, Boolean enabled, String email, String avatar, String bio){
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.avatar = avatar;
        this.bio = bio;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "email")
    private String email;
    
    @Column(name = "avatar")
    private String avatar;
    
    @Column(name = "bio")
    private String bio;
}
