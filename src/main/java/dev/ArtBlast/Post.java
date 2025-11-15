package dev.ArtBlast;

import org.springframework.data.annotation.Id;

public record Post(@Id Long id, String author, Boolean has_media, String media_link, String text_content) {
    
}
