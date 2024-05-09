package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.SoftDrink;
import com.jux.juxbar.Model.UserRequest;
import com.jux.juxbar.Service.CocktailService;
import com.jux.juxbar.Service.FavouritesService;
import com.jux.juxbar.Service.SoftDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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




    @PostMapping(value = "/favouritecocktails", consumes = {"application/json"})
    public Iterable<Cocktail> getFavouriteCocktails(@RequestBody UserRequest userRequest) {
        System.out.println("Received favCocktails request for user: " + userRequest.getUsername());
        String username = userRequest.getUsername();

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


    @PostMapping(value = "/favouritesoftdrinks", consumes = {"application/json"})
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
}
