package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Model.SoftDrink;
import com.jux.juxbar.Model.UserRequest;
import com.jux.juxbar.Service.CocktailService;
import com.jux.juxbar.Service.FavouritesService;
import com.jux.juxbar.Service.JuxBarUserService;
import com.jux.juxbar.Service.SoftDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RestController

public class FavouritesController {

    @Autowired
    FavouritesService favouritesService;
    @Autowired
    private CocktailService cocktailService;
    @Autowired
    private SoftDrinkService softDrinkService;
    @Autowired
    private JuxBarUserService juxBarUserService;


    //TODO
    // GESTION REQUETES VIA TOKEN POUR LES SOFT



    @GetMapping("/user/favouritecocktails")
    public Iterable<Cocktail> getFavouriteCocktails(Principal principal) {
        System.out.println("Received favCocktails request for user: " + principal.getName());
        String username = principal.getName();

        List<Integer> cocktailIds = favouritesService.getfavouriteCocktails(username);
        System.out.println(cocktailIds.toString());
        ArrayList<Cocktail> favouriteCocktails = new ArrayList<>();
        cocktailIds.forEach(cocktailId -> {
                    Optional<Cocktail> newFav = cocktailService.getCocktail(cocktailId);
            newFav.ifPresent(cocktail -> favouriteCocktails.add(newFav.get()));
                   }
        );
        return favouriteCocktails;
    }

    @PutMapping(value = "/user/favouritecocktail/{id}")
    public ResponseEntity<String> addFavoriteCocktail(@PathVariable Integer id, Principal principal){
        try {
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteCocktails());
            List<Integer> cocktailIds = favouritesService.getfavouriteCocktails(username);
            cocktailIds.add(id);

            juxBarUser.setFavourite_cocktails(cocktailIds.toString());
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok("done");
        }catch (Error e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @PostMapping(value = "/user/favouritesoftdrinks", consumes = {"application/json"})
    public Iterable<SoftDrink> getFavouriteSoftDrinks(@RequestBody UserRequest userRequest) {
        System.out.println("Received favSoftDrinks request for user: " + userRequest.getUsername());
        String username = userRequest.getUsername();

        List<Integer> softDrinksIds = favouritesService.getFavouriteSoftDrinks(username);
        System.out.println(softDrinksIds.toString());
        ArrayList<SoftDrink> favouriteSoftDrinks = new ArrayList<>();
        softDrinksIds.forEach(softDrinkId -> {
                    Optional<SoftDrink> newFav = softDrinkService.getSoftDrink(softDrinkId);
                    newFav.ifPresent(softDrink -> favouriteSoftDrinks.add(newFav.get()));
                }
        );
        return favouriteSoftDrinks;
    }

    @PutMapping(value = "/user/favouritesoftdrink/{id}")
    public ResponseEntity<String> addFavouriteSoftDrink(@PathVariable Integer id, Principal principal){
        try {
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavourite_softdrinks());
            List<Integer> softDrinksIds = favouritesService.getFavouriteSoftDrinks(username);
            softDrinksIds.add(id);

            juxBarUser.setFavourite_softdrinks(softDrinksIds.toString());
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok("done");
        }catch (Error e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

}
