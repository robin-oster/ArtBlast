package dev.ArtBlast;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ArtBlast.Entities.Post;
import dev.ArtBlast.Entities.User;
import dev.ArtBlast.Services.MyUserDetailsService;
import dev.ArtBlast.Services.MyUserPrincipal;
import dev.ArtBlast.Services.PostDataService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final PostDataService postDataService;

    @Autowired
    private final MyUserDetailsService myUserDetailsService;

    // get list of posts pending approval
    @GetMapping("/unapproved-posts")
    public ResponseEntity<List<Post>> getPendingPosts(Pageable pageable, Principal principal) {
        MyUserPrincipal currentUser = myUserDetailsService.loadUserByUsername(principal.getName());
        if(currentUser.getAuthorities().toString() == "ROLE_ADMIN"){
            Page<Post> page = postDataService.findByPending(true,
            PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "dateTime"))));
            return ResponseEntity.ok(page.getContent());
        }
        return ResponseEntity.notFound().build();
    }
    
    // get list of untrusted users
    @GetMapping("/untrusted-users")
    public ResponseEntity<List<User>> getUntrustedUsers(Pageable pageable, Principal principal) {
        MyUserPrincipal currentUser = myUserDetailsService.loadUserByUsername(principal.getName());
        if(currentUser.getAuthorities().toString() == "ROLE_ADMIN"){
            Page<User> page = myUserDetailsService.findByTrusted(false,
            PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "dateTime"))));
            return ResponseEntity.ok(page.getContent());
        }
        return ResponseEntity.notFound().build();
    }

    // approve a post that is pending approval
    @PutMapping("approve-post/{id}")
    public ResponseEntity<Void> approvePost(@PathVariable Long id, @RequestBody Post updatedPost, Principal principal) {
        MyUserPrincipal currentUser = myUserDetailsService.loadUserByUsername(principal.getName());
        if(currentUser.getAuthorities().toString() == "ROLE_ADMIN"){
            Post originalPost = postDataService.findByIdAndUsername(id, updatedPost.getUsername());
            Post postToSave = new Post(originalPost.getId(), originalPost.getUsername(), originalPost.getHasMedia(),
            originalPost.getMediaLink(), originalPost.getTextContent(), originalPost.getDateTime(), originalPost.getParentId(), false, originalPost.getTimesReported());
            postDataService.save(postToSave);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // set a user to "trusted", removing the need to grant manual approval
    @PutMapping("trust-user/{id}")
    public ResponseEntity<Void> trustUser(@PathVariable Long id, @RequestBody Post updatedUser, Principal principal) {
        MyUserPrincipal currentUser = myUserDetailsService.loadUserByUsername(principal.getName());
        Optional<User> userToTrust = myUserDetailsService.findById(id);
        if(currentUser.getAuthorities().toString() == "ROLE_ADMIN" && userToTrust.isPresent()){
            MyUserPrincipal originalUser = myUserDetailsService.loadUserByUsername(userToTrust.get().getUsername());
            User userToSave = new User(originalUser.getId(), originalUser.getUsername(), originalUser.getPassword(), originalUser.getEnabled(), originalUser.getEmail(),
            originalUser.getAvatar(), originalUser.getBio(), originalUser.getAuthorities().toString(), true);
            myUserDetailsService.save(userToSave);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // permanently delete a post pending approval
    @DeleteMapping("/deny-post/{id}")
    public ResponseEntity<Void> denyPost(@PathVariable Long id, @RequestBody Post updatedPost, Principal principal) {
        MyUserPrincipal currentUser = myUserDetailsService.loadUserByUsername(principal.getName());
        if(currentUser.getAuthorities().toString() == "ROLE_ADMIN"){
            postDataService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
