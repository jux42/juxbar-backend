package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.PersonalCocktail;
import com.jux.juxbar.Service.PersonalCocktailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
public class PersonalCocktailController {


    @Autowired
    PersonalCocktailService personalCocktailService;


    @GetMapping(value = "/user/personalcocktails")
    public Iterable<PersonalCocktail> getPersonalCocktails(Principal principal) {
        String userName = principal.getName();
        log.info(userName);
        return personalCocktailService.getPersonalCocktails(userName);
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
    public  ResponseEntity<String> removePersonalCocktail(@PathVariable int id, Principal principal) {
        String userName = principal.getName();
        String output =  personalCocktailService.removePersonalCocktail(id, userName);
        return ResponseEntity.ok(output);
    }

}
