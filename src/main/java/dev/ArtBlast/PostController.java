package dev.ArtBlast;

import java.net.URI;
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

import dev.ArtBlast.Services.MediaService;
import dev.ArtBlast.Services.PostDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final MediaService mediaService;
    private final PostDataService postDataService;

    @PostMapping("/createNew")
    private ResponseEntity<Void> createPost(@RequestBody Post postRequest, @RequestParam(name="file", required=false) MultipartFile file,
        UriComponentsBuilder ucb, Principal principal){
        
        Post newPostWithAuthor;
        String imagePath;

        // upload image if post has media
        if(postRequest.has_media() == true){
            try {
                imagePath = mediaService.uploadFile(file);
                newPostWithAuthor = new Post(null, principal.getName(), postRequest.has_media(), imagePath, postRequest.text_content());
            } catch (FileUploadException e){
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
        } else {
            newPostWithAuthor = new Post(null, principal.getName(), postRequest.has_media(), null, postRequest.text_content());
        }

        Post savedPost = postDataService.save(newPostWithAuthor);
        URI locationOfPost = ucb
            .path("posts/{id}")
            .buildAndExpand(savedPost.id())
            .toUri();

        return ResponseEntity.created(locationOfPost).build();
    }
    
}
