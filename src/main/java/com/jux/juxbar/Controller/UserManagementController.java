package com.jux.juxbar.Controller;

import com.jux.juxbar.Configuration.CustomUserDetailsService;
import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Repository.JuxBarUserRepository;
import com.jux.juxbar.Service.JuxBarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class UserManagementController {

    @Autowired
    JuxBarUserRepository juxBarUserRepository;
    @Autowired
    JuxBarUserService juxBarUserService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @GetMapping("admin/users")
    public ResponseEntity<Iterable<JuxBarUser>> getUsers() {

        Iterable<JuxBarUser> users = juxBarUserService.getAllJuxBarUsers().stream().map(juxBarUser -> {
            juxBarUser.setPassword("******");
            return juxBarUser;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(users);

    }

    @PostMapping("admin/user")
    public ResponseEntity<?> createUser(@RequestParam String username, @RequestParam String password) {
        if (juxBarUserService.getJuxBarUserByUsername(username) != null) {
            return ResponseEntity.ok("cet utilisateur existe déjà !!");
        }

        juxBarUserService.saveJuxBarUser(username, password, customUserDetailsService);
        return juxBarUserService.getJuxBarUserByUsername(username) != null
                ? ResponseEntity.ok("utilisateur créé avec le nom : " + username + " !!")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'utilisateur !!");
    }
}








