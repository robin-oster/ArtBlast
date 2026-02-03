package dev.ArtBlast;

import java.lang.module.ResolutionException;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import dev.ArtBlast.Entities.User;
import dev.ArtBlast.Services.MediaService;
import dev.ArtBlast.Services.MyUserDetailsService;
import dev.ArtBlast.Services.MyUserPrincipal;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    @Autowired
    private final MyUserDetailsService userDetailsService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final MediaService mediaService;

    @GetMapping("/{username}")
    private ResponseEntity<User> retrieveUser(@RequestParam String username, Principal principal) {
        MyUserPrincipal userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails != null && userDetails.isEnabled()){
            return ResponseEntity.ok(new User(userDetails.getId(), userDetails.getUsername(), null, true,
            null, userDetails.getAvatar(), userDetails.getBio(), null, null));
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/newUser")
    public ResponseEntity<UserDetails> createUser(@RequestBody User request, UriComponentsBuilder ucb) {
        
        String cryptPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User(null, request.getUsername(), cryptPassword, false, 
            request.getEmail(), null, null, "ROLE_USER", false);
        
        
        try{
            User savedUser = userDetailsService.save(newUser);

            URI savedLocation = ucb
            .path("/user/{username}")
            .buildAndExpand(savedUser.getUsername())
            .toUri();

            return ResponseEntity.created(savedLocation).build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        }
    }

    // lets user update aspects of their account that they have control over
    // (username, password, email, avatar, bio)
    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUserDetails(@PathVariable String username, @RequestBody User userUpdate, Principal principal) {
        MyUserPrincipal userDetails = userDetailsService.loadUserByUsername(principal.getName());
        String updatedUsername = userDetails.getUsername();
        String updatedPassword = userDetails.getPassword();
        String updatedEmail = userDetails.getEmail();
        String updatedAvatar = userDetails.getAvatar();
        String updatedBio = userDetails.getBio();
        
        if(updatedUsername != userUpdate.getUsername()) updatedUsername = userUpdate.getUsername();
        if(updatedPassword != passwordEncoder.encode(userUpdate.getPassword())) updatedPassword = passwordEncoder.encode(userUpdate.getPassword());
        if(updatedEmail != userUpdate.getEmail()) updatedEmail = userUpdate.getEmail();
        if(updatedAvatar != userUpdate.getAvatar()) updatedAvatar = userUpdate.getAvatar();
        if(updatedBio != userUpdate.getBio()) updatedBio = userUpdate.getBio();

        if(userDetails != null && userUpdate.getId() == userDetails.getId()){
            //ensure id doesnt change
            User updatedUser = new User(userDetails.getId(), updatedUsername, updatedPassword, true, updatedEmail, updatedAvatar, 
                updatedBio, userDetails.getAuthorities().toString(), userDetails.getTrusted());
            userDetailsService.save(updatedUser);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("follow/{id}")
    public ResponseEntity<Void> followUser(@PathVariable Long id, @RequestBody User userToFollow, Principal principal) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteUser(@PathVariable Long id, Principal principal){
        if(userDetailsService.getId(principal.getName()) != id){
            return ResponseEntity.notFound().build();
        }
        userDetailsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
