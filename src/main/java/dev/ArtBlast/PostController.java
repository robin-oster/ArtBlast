package dev.ArtBlast;

import java.net.URI;
import java.security.Principal;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
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
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final MediaService mediaService;

    @Autowired
    private final PostDataService postDataService;

    @PostMapping("/createNew")
    private ResponseEntity<Void> createPost(@RequestBody Post postRequest, String media_key,
        UriComponentsBuilder ucb, Principal principal){
        
        Post newPostWithAuthor;
        String imagePath;

        // upload image if post has media
        if(postRequest.has_media() == true){
            newPostWithAuthor = new Post(null, principal.getName(), postRequest.has_media(), media_key, postRequest.text_content());
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

    @GetMapping("/{id}")
    public ResponseEntity<Post> retrievePost(@PathVariable Long id, Principal principal) {
        Post post = postDataService.findByIdAndAuthor(id, principal.getName());
        if (post != null){
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
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
