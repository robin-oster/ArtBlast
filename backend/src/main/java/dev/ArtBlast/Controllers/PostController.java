package dev.ArtBlast.Controllers;

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

import dev.ArtBlast.Entities.Post;
import dev.ArtBlast.Services.MediaService;
import dev.ArtBlast.Services.PostDataService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



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
        
        Post newPostWithUsername;

        // upload image if post has media
        if(postRequest.getHasMedia() == true){
            newPostWithUsername = new Post(null, principal.getName(), postRequest.getHasMedia(), media_key, postRequest.getTextContent(), postRequest.getDateTime(), null, true, 0L);
        } else {
            newPostWithUsername = new Post(null, principal.getName(), postRequest.getHasMedia(), null, postRequest.getTextContent(), postRequest.getDateTime(), null, true, 0L);
        }

        Post savedPost = postDataService.save(newPostWithUsername);
        URI locationOfPost = ucb
            .path("/posts/{id}")
            .buildAndExpand(savedPost.getId())
            .toUri();

        return ResponseEntity.created(locationOfPost).build();
    }

    @PostMapping("/{postId}/reply")
    public ResponseEntity<Post> createNewReply(@PathVariable Long postId, @RequestBody Post postRequest, String media_key,
        UriComponentsBuilder ucb, Principal principal){

        Post newReplyWithUsername;

        if(postRequest.getHasMedia() == true){
            newReplyWithUsername = new Post(null, principal.getName(), postRequest.getHasMedia(), media_key, 
            postRequest.getTextContent(), postRequest.getDateTime(), postId, true, 0L);
        } else {
            newReplyWithUsername = new Post(null, principal.getName(), postRequest.getHasMedia(), null, 
            postRequest.getTextContent(), postRequest.getDateTime(), postId, true, 0L);
        }

        Post savedPost = postDataService.save(newReplyWithUsername);
        URI locationOfPost = ucb
            .path("/posts/{id}")
            .buildAndExpand(savedPost.getId())
            .toUri();

        return ResponseEntity.created(locationOfPost).build();
    }

        // can be called inside createNew to pass media_key in
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

    @GetMapping("/{requestedId}")
    private ResponseEntity<Post> retrievePost(@PathVariable Long requestedId, Principal principal) {
        Post post = postDataService.findByIdAndUsername(requestedId, principal.getName());
        System.out.println("POST: " + principal.getName());
        if (post != null){
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{username}")
    private ResponseEntity<List<Post>> retrieveAllUserPosts(@PathVariable String username, Pageable pageable, Principal principal){
        Page<Post> page = postDataService.findByUsernameAndTimesReportedLessThan(username, 1L,
            PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "dateTime"))));
        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("report/{id}")
    public ResponseEntity<Void> reportPost(@PathVariable Long id, Principal principal) {
        Post originalPost = postDataService.findById(id);
        Post postToSave = new Post(id, originalPost.getUsername(), originalPost.getHasMedia(), originalPost.getMediaLink(),
        originalPost.getTextContent(), originalPost.getDateTime(), originalPost.getParentId(), originalPost.getPending(),
        originalPost.getTimesReported() + 1);
        postDataService.save(postToSave);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{requestedId}")
    private ResponseEntity<Void> deletePost(@PathVariable Long requestedId, Principal principal){
        if(!postDataService.existsByIdAndUsername(requestedId, principal.getName())){
            return ResponseEntity.notFound().build();
        }
        postDataService.deleteById(requestedId);
        return ResponseEntity.noContent().build();
    }
}
