package com.jux.juxbar.controller;

import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.service.PersonalCocktailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PersonalCocktailController {

    private final PersonalCocktailService personalCocktailService;


    @GetMapping(value = "/user/personalcocktails")
    public Iterable<PersonalCocktail> getPersonalCocktails(Principal principal) {
        String userName = principal.getName();
        log.info(userName);
        return personalCocktailService.getPersonalCocktails(userName);
    }

    //surcharge pour accès front en admin
    @PostMapping("/user/personalcocktails")
    public Iterable<PersonalCocktail> getPersonalCocktails(@RequestParam String username) {
        log.info(username);
        return personalCocktailService.getPersonalCocktails(username);
    }

    @PostMapping(value = "/user/personalcocktail")
    public ResponseEntity<String> savePersonalCocktail(@RequestBody PersonalCocktail personalCocktail) {
        log.info(String.valueOf(personalCocktail));
        String output = personalCocktailService.savePersonalCocktail(personalCocktail);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/user/personalcocktail/{id}")
    public PersonalCocktail getPersonalCocktail(@PathVariable int id, Principal principal) {
        String userName = principal.getName();
        return personalCocktailService.getPersonalCocktail(id, userName);
    }

    @DeleteMapping("/user/personalcocktail/{id}")
    public ResponseEntity<String> removePersonalCocktail(@PathVariable int id, Principal principal) {
        String userName = principal.getName();
        String output = personalCocktailService.removePersonalCocktail(id, userName);
        return ResponseEntity.ok(output);
    }

    @PutMapping("/user/personalcocktail/trash/{id}")
    public ResponseEntity<String> trashPersonalCocktail(@PathVariable int id, Principal principal) {
        String userName = principal.getName();
        String output = personalCocktailService.trashPersonalCocktail(id, userName);
        return ResponseEntity.ok(output);
    }

}
