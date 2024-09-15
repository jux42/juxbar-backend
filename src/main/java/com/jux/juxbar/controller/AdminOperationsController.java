package com.jux.juxbar.controller;


import com.jux.juxbar.service.AdminOperationsService;
import com.jux.juxbar.service.JuxBarUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminOperationsController {


    private final AdminOperationsService adminOperationsService;

    @GetMapping("admin/trashlist/{username}")
    public ResponseEntity<List<Integer>> listTrashedCocktailsOfUser(@PathVariable String username) {

        return ResponseEntity.ok(adminOperationsService.listTrashedCocktailsOfUser(username));

    }

    @GetMapping("admin/untrash/{username}/{id}")
    public ResponseEntity<String> restoreOneTrashedCocktail(@PathVariable Integer id, @PathVariable String username){

        return ResponseEntity.ok(adminOperationsService.restoreOneTrashedCocktail(username, id));
    }

    @GetMapping("admin/untrashall/{username}")
    public ResponseEntity<String> restoreAllCocktailsOfUser(@PathVariable String username){
        return ResponseEntity.ok(adminOperationsService.restoreAllCocktailsOfUser(username));
    }



}
