package com.jux.juxbar.controller;

import com.jux.juxbar.configuration.CustomUserDetailsService;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.service.JuxBarUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserManagementController {

    private final JuxBarUserService juxBarUserService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("admin/users")
    public ResponseEntity<Iterable<JuxBarUser>> getUsers() {

        List<JuxBarUser> list = new ArrayList<>();
        for (JuxBarUser barUser : juxBarUserService.getAllJuxBarUsers()) {
            barUser.setPassword("******");
            list.add(barUser);
        }
        Iterable<JuxBarUser> users = list;

        return ResponseEntity.ok(users);

    }

    @PostMapping("admin/user")
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam String password) {
        if (juxBarUserService.getJuxBarUserByUsername(username) != null) {
            return ResponseEntity.ok("cet utilisateur existe déjà !!");
        }

        juxBarUserService.saveJuxBarUser(username, password);
        return juxBarUserService.getJuxBarUserByUsername(username) != null
                ? ResponseEntity.ok("utilisateur créé avec le nom : " + username + " !!")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'utilisateur !!");
    }

    @PutMapping("admin/userpassword/{username}")
    public ResponseEntity<String> changeUserPassword(@PathVariable String username, @RequestParam String newPassword){
        return ResponseEntity.ok(customUserDetailsService.changeUserPassword(username, newPassword));
    }


    @GetMapping("admin/reactivate/{username}")
    public ResponseEntity<String> reactivateUser(@PathVariable String username) {
        return ResponseEntity.ok(juxBarUserService.reactivateUser(username));
    }

    @GetMapping("admin/disable/{username}")
    public ResponseEntity<String> inactivateUser(@PathVariable String username) {
        return ResponseEntity.ok(juxBarUserService.disableUser(username));
    }

}








