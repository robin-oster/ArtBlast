package dev.ArtBlast.Entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FOLLOWS")
@Getter
@Setter
public class Follow {
    
    public Follow(){}

    public Follow(Long id, User followingUser, User followedUser, Timestamp dateTime){
        this.id = id;
        this.followingUser = followingUser;
        this.followedUser = followedUser;
        this.dateTime = dateTime;
    }

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user.id")
    private User followingUser;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user.id")
    private User followedUser;

    @Column(name = "date_time")
    private Timestamp dateTime;
}
