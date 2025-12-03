package dev.ArtBlast;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    Post(Long id, String author, Boolean hasMedia, String mediaLink, String textContent, String date_time){
        this.id = id;
        this.author = author;
        this.hasMedia = hasMedia;
        this.mediaLink = mediaLink;
        this.textContent = textContent;
        this.dateTime = date_time;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(name = "author")
    private String author;

    @Column(name = "has_media")
    private Boolean hasMedia;

    @Column(name = "media_link")
    private String mediaLink;

    @Column(name = "text_content")
    private String textContent;
    
    @Column(name = "date_time")
    private String dateTime;
}
