package com.jux.juxbar.controller;

import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.service.FavouritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class FavouritesController {

    private final FavouritesService favouritesService;


    @GetMapping("/user/favouritecocktails")
    public Iterable<Cocktail> getFavouriteCocktails(Principal principal) {
        return favouritesService.getFavouriteCocktails(principal);
    }

    @PutMapping(value = "/user/favouritecocktail/{id}")
    public ResponseEntity<String> addFavoriteCocktail(@PathVariable Integer id, Principal principal) {
        return favouritesService.addFavoriteCocktail(id, principal);
    }

    @PutMapping(value = "/user/rmfavouritecocktail/{id}")
    public ResponseEntity<String> removeFavoriteCocktail(@PathVariable Integer id, Principal principal) {
        return favouritesService.removeFavoriteCocktail(id, principal);
    }

    @GetMapping(value = "/user/favouritesoftdrinks")
    public Iterable<SoftDrink> getFavouriteSoftDrinks(Principal principal) {
        return favouritesService.getFavouriteSoftDrinks(principal);
    }

    @PutMapping(value = "/user/favouritesoftdrink/{id}")
    public ResponseEntity<String> addFavoriteSoftDrink(@PathVariable Integer id, Principal principal) {
        return favouritesService.addFavoriteSoftDrink(id, principal);
    }

    @PutMapping(value = "/user/rmfavouritesoftdrink/{id}")
    public ResponseEntity<String> removeFavoriteSoftDrink(@PathVariable Integer id, Principal principal) {
        return favouritesService.removeFavoriteSoftDrink(id, principal);
    }

}