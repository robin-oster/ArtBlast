package dev.ArtBlast.Entities;

import java.sql.Timestamp;

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
@Table(name = "POSTS")
@Getter
@Setter
public class Post {

    public Post(){}

    public Post(Long id, String username, Boolean hasMedia, String mediaLink, String textContent, Timestamp dateTime, 
        Long parentId, Boolean pending, Long timesReported){
        this.id = id;
        this.username = username;
        this.hasMedia = hasMedia;
        this.mediaLink = mediaLink;
        this.textContent = textContent;
        this.dateTime = dateTime;
        this.parentId = parentId;
        this.pending = pending;
        this.timesReported = timesReported;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(name = "username")
    private String username;

    @Column(name = "has_media")
    private Boolean hasMedia;

    @Column(name = "media_link")
    private String mediaLink;

    @Column(name = "text_content")
    private String textContent;
    
    @Column(name = "date_time")
    private Timestamp dateTime;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "pending_approval")
    private Boolean pending;

    @Column(name = "times_reported")
    private Long timesReported;
}
