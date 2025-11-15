package dev.ArtBlast;

import java.security.Principal;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final MediaService mediaService;

    @PostMapping("/createNew")
    private ResponseEntity<Void> createPost(@RequestBody Post postRequest, UriComponentsBuilder ucb, 
        Principal principal){
            Post newPostWithAuthor = new Post(null, principal.getName(), null, null, null);
        
        ResponseEntity<Void> response = new ResponseEntity<Void>(HttpStatus.OK);
        return response;
    }

    @PostMapping("/uploadMedia")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String key = "k";
        try{
            key = mediaService.uploadFile(file);
        } catch (FileUploadException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(key);
    }
    
    
}
