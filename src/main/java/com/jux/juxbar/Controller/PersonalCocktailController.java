package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.PersonalCocktail;
import com.jux.juxbar.Model.UserRequest;
import com.jux.juxbar.Service.PersonalCocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class PersonalCocktailController {


    @Autowired
    PersonalCocktailService personalCocktailService;



    @PostMapping(value = "/personalcocktails", consumes = {"application/json"})
    public Iterable<PersonalCocktail> getPersonalCocktails(@RequestBody UserRequest userRequest){
        String userName = userRequest.getUsername();
        System.out.println(userName);
        return personalCocktailService.getPersonalCocktails(userName);
    }

//    @GetMapping("/personalcocktail/{id}")
//    public Optional<PersonalCocktail> getCocktail(@PathVariable int id){
//    String userName = "jux";
//        return personalCocktailService.getPersonalCocktail(id,userName );
//                    }
}
