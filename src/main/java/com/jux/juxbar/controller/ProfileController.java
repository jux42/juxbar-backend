package com.jux.juxbar.controller;

import com.jux.juxbar.component.TextSanitizer;
import com.jux.juxbar.configuration.CustomUserDetailsService;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.service.JuxBarUserService;
import com.jux.juxbar.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController {


    private final ProfileService profileService;
    private final JuxBarUserService juxBarUserService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("user/details")
    public ResponseEntity<JuxBarUser> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(profileService.getCurrentUser(principal.getName()));
    }

    @PostMapping("user/picture")
    public ResponseEntity<String> updateProfilePicture(@RequestBody byte[] pictureBytes, Principal principal) {
        try {
            return ResponseEntity.ok(profileService.updateProfilePicture(principal.getName(), pictureBytes));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload picture");
        }
    }

    @PostMapping("user/aboutme")
    public ResponseEntity<String> updateAboutMeText(@RequestParam String aboutMe, Principal principal) {
        if (aboutMe.length()>1000){
            return ResponseEntity.badRequest().body("text is too long (1000 characters maximum)");
        }
        if (aboutMe.isEmpty()) {
            return ResponseEntity.badRequest().body("no text received");
        }
        aboutMe = TextSanitizer.sanitizeText(aboutMe);
        return ResponseEntity.ok(profileService.updateAboutMeText(principal.getName(), aboutMe));
    }
    @PostMapping("user/aboutme/securize")
    public ResponseEntity<Boolean> secureText(@RequestParam String aboutMe, Principal principal) {
        return ResponseEntity.ok(TextSanitizer.securize(aboutMe));
    }

    @GetMapping("/user/{username}/mypicture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String username) {
        log.info("get profile picture for {}", username);
        JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
        return ResponseEntity.ok(juxBarUser.getProfilePicture());
    }

    @PutMapping("user/{username}/password")
    public ResponseEntity<String> changeUserPassword(@PathVariable String username, @RequestParam String newPassword){
        return ResponseEntity.ok(customUserDetailsService.changeUserPassword(username, newPassword));

    }

    @DeleteMapping("user/{username}")
    public ResponseEntity<String> deleteAccount(@PathVariable String username, Principal principal) {
        log.info("delete account for {}", username);
        if(!username.equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }
        return ResponseEntity.ok(profileService.deleteAccount(username));

    }
}
