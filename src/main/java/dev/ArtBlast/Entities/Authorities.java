package dev.ArtBlast.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "AUTHORITIES")
@Getter
@Setter
public class Authorities {
    
    public Authorities(){}

    public Authorities(String username, String authority){
        this.username = username;
        this.authority = authority;
    }

    @Column
    private String username;

    @Column
    private String authority;
}
