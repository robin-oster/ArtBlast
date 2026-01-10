package dev.ArtBlast;

import java.net.URI;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.ArtBlast.Entities.User;
import dev.ArtBlast.Services.MyUserDetailsService;
import dev.ArtBlast.Services.MyUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    @Autowired
    private final MyUserDetailsService userDetailsService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/{username}")
    private ResponseEntity<UserDetails> retrieveUser(@RequestParam String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println("POST: " + userDetails.getUsername());
        if(userDetails != null){
            return ResponseEntity.ok(userDetails);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/newUser")
    public ResponseEntity<UserDetails> createUser(@RequestBody User request, UriComponentsBuilder ucb) {
        
        String cryptPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User(null, request.getUsername(), cryptPassword, request.getEnabled(), 
            request.getEmail(), request.getAvatar(), request.getBio(), request.getAuthority());
        
        System.out.println("SAVED USER: " + newUser);
        User savedUser = userDetailsService.save(newUser);
        URI savedLocation = ucb
            .path("/user/{username}")
            .buildAndExpand(savedUser.getUsername())
            .toUri();

        return ResponseEntity.created(savedLocation).build();
    }
    
}
