package dev.ArtBlast;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Post(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id, String author, Boolean has_media, String media_link, String text_content) {
    
}
