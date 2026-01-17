package dev.ArtBlast;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.ArtBlast.Entities.Like;
import dev.ArtBlast.Services.LikeDataService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    @Autowired
    private final LikeDataService likeDataService;

    @PostMapping("/addLike")
    public ResponseEntity<Like> addLike(@RequestBody Like like, UriComponentsBuilder ucb, Principal principal) {
        Like likeToSave = new Like(null, like.getPost(), like.getUser(), like.getDateTime());
        likeDataService.save(likeToSave);

        URI locationOfLike = ucb
            .path("/likes/{id}")
            .buildAndExpand(likeToSave.getId())
            .toUri();

        return ResponseEntity.created(locationOfLike).build();
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long requestedId, Principal principal){
        Optional<Like> likeToDelete = likeDataService.findById(requestedId);
        if(likeToDelete != null){
            likeDataService.deleteById(likeToDelete.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    
}
