package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.PersonalCocktail;
import com.jux.juxbar.Service.PersonalCocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@CrossOrigin
@RestController
public class PersonalCocktailController {


    @Autowired
    PersonalCocktailService personalCocktailService;



    @GetMapping(value = "/user/personalcocktails")
    public Iterable<PersonalCocktail> getPersonalCocktails(Principal principal){
        String userName = principal.getName();
        System.out.println(userName);
        return personalCocktailService.getPersonalCocktails(userName);
    }

//    @GetMapping("/personalcocktail/{id}")
//    public Optional<PersonalCocktail> getCocktail(@PathVariable int id){
//    String userName = "jux";
//        return personalCocktailService.getPersonalCocktail(id,userName );
//                    }
}
