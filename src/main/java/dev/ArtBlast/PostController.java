package dev.ArtBlast;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/posts")
public class PostController {

    @PostMapping
    private ResponseEntity<Void> createPost(@RequestBody Post post, UriComponentsBuilder ucb, 
        Principal principal){
        
        ResponseEntity<Void> response = new ResponseEntity<Void>(HttpStatus.OK);
        return response;
    }
    
}
