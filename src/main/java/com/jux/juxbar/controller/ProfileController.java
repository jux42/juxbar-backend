package com.jux.juxbar.controller;

import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.service.JuxBarUserService;
import com.jux.juxbar.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ProfileController {


    private final JuxBarUserService juxBarUserService;
    private final ProfileService profileService;

    @GetMapping("user/details")
    public ResponseEntity<JuxBarUser> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(profileService.getCurrentUser(principal.getName()));
    }

    @PostMapping("user/picture")
    public ResponseEntity<String> updateProfilePicture(@RequestParam byte[] picture, Principal principal) {
        return ResponseEntity.ok(profileService.updateProfilePicture(principal.getName(), picture));
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
}
