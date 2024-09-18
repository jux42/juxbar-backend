package com.jux.juxbar.controller;

import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.service.JuxBarUserService;
import com.jux.juxbar.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController {


    private final ProfileService profileService;
    private final JuxBarUserService juxBarUserService;

    @GetMapping("user/details")
    public ResponseEntity<JuxBarUser> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(profileService.getCurrentUser(principal.getName()));
    }

    @PostMapping("user/picture")
    public ResponseEntity<String> updateProfilePicture(@RequestParam("picture") MultipartFile picture, Principal principal) {
        try {
            byte[] pictureBytes = picture.getBytes();
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
        return ResponseEntity.ok(profileService.updateAboutMeText(principal.getName(), aboutMe));
    }

    @GetMapping("/user/mypicture")
    public ResponseEntity<byte[]> getProfilePicture(Principal principal) {
        JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(principal.getName());
        return ResponseEntity.ok(juxBarUser.getProfilePicture());
    }
}
