package com.jux.juxbar.controller;

import com.jux.juxbar.configuration.CustomUserDetailsService;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.service.JuxBarUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserManagementController {

    private final JuxBarUserService juxBarUserService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("admin/users")
    public ResponseEntity<Iterable<JuxBarUser>> getUsers() {

        Iterable<JuxBarUser> users = juxBarUserService.getAllJuxBarUsers().stream().map(juxBarUser -> {
            juxBarUser.setPassword("******");
            return juxBarUser;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(users);

    }

    @PostMapping("admin/user")
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam String password) {
        if (juxBarUserService.getJuxBarUserByUsername(username) != null) {
            return ResponseEntity.ok("cet utilisateur existe déjà !!");
        }

        juxBarUserService.saveJuxBarUser(username, password, customUserDetailsService);
        return juxBarUserService.getJuxBarUserByUsername(username) != null
                ? ResponseEntity.ok("utilisateur créé avec le nom : " + username + " !!")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'utilisateur !!");
    }
}








