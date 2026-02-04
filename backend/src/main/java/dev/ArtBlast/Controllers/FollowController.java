package dev.ArtBlast.Controllers;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.ArtBlast.Entities.Follow;
import dev.ArtBlast.Entities.User;
import dev.ArtBlast.Services.FollowDataService;
import dev.ArtBlast.Services.MyUserDetailsService;
import dev.ArtBlast.Services.MyUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    @Autowired
    FollowDataService followDataService;

    @Autowired
    MyUserDetailsService userDetailsService;

    //get a user's list of followers
    @GetMapping("/followers-list/{username}")
    public ResponseEntity<List<Follow>> getFollowersList(@PathVariable String username, Pageable pageable, Principal principal) {
        MyUserPrincipal user = userDetailsService.loadUserByUsername(username);
        User userToFind = new User(user.getId(), user.getUsername(), user.getPassword(), user.getEnabled(),
            user.getEmail(), user.getAvatar(), user.getBio(), user.getAuthorities().toString(), user.getTrusted());

        Page<Follow> page = followDataService.findByFollowedUser(userToFind, 
            PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.DESC, "dateTime")))
        );
        return ResponseEntity.ok(page.getContent());
    }

    //get a user's list of other users they are following
    @GetMapping("/following-list/{username}")
    public ResponseEntity<List<Follow>> getFollowingList(@PathVariable String username, Pageable pageable, Principal principal) {
        MyUserPrincipal user = userDetailsService.loadUserByUsername(username);
        User userToFind = new User(user.getId(), user.getUsername(), user.getPassword(), user.getEnabled(),
            user.getEmail(), user.getAvatar(), user.getBio(), user.getAuthorities().toString(), user.getTrusted());

        Page<Follow> page = followDataService.findByFollowingUser(userToFind, 
            PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.DESC, "dateTime")))
        );
        return ResponseEntity.ok(page.getContent());
    }

    // Creates new follow entry
    // Add User A to another User B's followers list / add User B to User A's following list
    @PostMapping("/newFollow")
    public ResponseEntity<Void> createNewFollow(@RequestBody Follow followRequest, UriComponentsBuilder ucb) {
        Follow newFollow = new Follow(null, followRequest.getFollowingUser(), followRequest.getFollowedUser(),
            followRequest.getDateTime());
        
        Follow savedFollow = followDataService.save(newFollow);
        URI locationOfFollow = ucb
            .path("/follows/{id}")
            .buildAndExpand(savedFollow.getId())
            .toUri();
        
        return ResponseEntity.created(locationOfFollow).build();
    }
    
    
}
