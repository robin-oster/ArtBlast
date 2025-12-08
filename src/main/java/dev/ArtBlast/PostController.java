package dev.ArtBlast;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import org.springframework.web.bind.annotation.DeleteMapping;
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
        if(postRequest.getHasMedia() == true){
            newPostWithAuthor = new Post(null, principal.getName(), postRequest.getHasMedia(), media_key, postRequest.getTextContent(), postRequest.getDateTime());
        } else {
            newPostWithAuthor = new Post(null, principal.getName(), postRequest.getHasMedia(), null, postRequest.getTextContent(), postRequest.getDateTime());
        }

        Post savedPost = postDataService.save(newPostWithAuthor);
        URI locationOfPost = ucb
            .path("/posts/{id}")
            .buildAndExpand(savedPost.getId())
            .toUri();

        return ResponseEntity.created(locationOfPost).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<Post> retrievePost(@PathVariable Long requestedId, Principal principal) {
        Post post = postDataService.findByIdAndAuthor(requestedId, principal.getName());
        System.out.println("POST: " + principal.getName());
        if (post != null){
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    private ResponseEntity<List<Post>> retrieveAllUserPosts(Pageable pageable, Principal principal){
        Page<Post> page = postDataService.findByAuthor(principal.getName(),
            PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "dateTime"))));
        return ResponseEntity.ok(page.getContent());
    }

    @DeleteMapping("/{requestedId}")
    private ResponseEntity<Void> deletePost(@PathVariable Long requestedId, Principal principal){
        if(!postDataService.existsByIdAndAuthor(requestedId, principal.getName())){
            return ResponseEntity.notFound().build();
        }
        postDataService.deleteById(requestedId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/uploadMedia")
    private ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String key = "k";
        try{
            key = mediaService.uploadFile(file);
        } catch (FileUploadException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(key);
    }
    
}
