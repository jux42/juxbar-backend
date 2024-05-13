package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Model.SoftDrink;
import com.jux.juxbar.Service.CocktailService;
import com.jux.juxbar.Service.FavouritesService;
import com.jux.juxbar.Service.JuxBarUserService;
import com.jux.juxbar.Service.SoftDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;


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
            System.out.println("in the addFav with id : " + id);
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteCocktails());
            List<Integer> cocktailIds = favouritesService.getfavouriteCocktails(username);
            cocktailIds.add(id);
            Collections.sort(cocktailIds);

            juxBarUser.setFavourite_cocktails(cocktailIds.toString().replace("[", "")
                                                                    .replace("]", "")
                                                                    .replace(" ", ""));
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("ajout OK");
        }catch (Error e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @PutMapping(value = "/user/rmfavouritecocktail/{id}")
    public ResponseEntity<String> removeFavoriteCocktail(@PathVariable Integer id, Principal principal){
        try {
            System.out.println("in the rmFav with id : " + id);
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteCocktails());
            List<Integer> cocktailIds = favouritesService.getfavouriteCocktails(username);
            cocktailIds.remove(id);
            Collections.sort(cocktailIds);

            juxBarUser.setFavourite_cocktails(cocktailIds.toString().replace("[", "")
                    .replace("]", "")
                    .replace(" ", ""));
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("suppression OK");
        }catch (Error e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    @GetMapping(value = "/user/favouritesoftdrinks")
    public Iterable<SoftDrink> getFavouriteSoftDrinks(Principal principal) {
        System.out.println("Received favSoftDrinks request for user: " + principal.getName());
        String username = principal.getName();

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
    public ResponseEntity<String> addFavoriteSoftDrink(@PathVariable Integer id, Principal principal){
        try {
            System.out.println("in the addFav soft with id : " + id);
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteSoftDrinks());
            List<Integer> softDrinksIds = favouritesService.getFavouriteSoftDrinks(username);
            softDrinksIds.add(id);
            Collections.sort(softDrinksIds);

            juxBarUser.setFavourite_softdrinks(softDrinksIds.toString().replace("[", "")
                    .replace("]", "")
                    .replace(" ", ""));
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("ajout soft OK");
        }catch (Error e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }
    @PutMapping(value = "/user/rmfavouritesoftdrink/{id}")
    public ResponseEntity<String> removeFavoriteSoftDrink(@PathVariable Integer id, Principal principal){
        try {
            System.out.println("in the rmFav soft with id : " + id);
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteSoftDrinks());
            List<Integer> softDrinksIds = favouritesService.getFavouriteSoftDrinks(username);
            softDrinksIds.remove(id);
            Collections.sort(softDrinksIds);

            juxBarUser.setFavourite_softdrinks(softDrinksIds.toString().replace("[", "")
                    .replace("]", "")
                    .replace(" ", ""));
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("suppression OK");
        }catch (Error e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }


}
