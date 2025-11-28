package dev.ArtBlast;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Post(@Id Long id, String author, Boolean has_media, String media_link, String text_content) {
    
}
