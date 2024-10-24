package com.jux.juxbar.service;

import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.model.SoftDrink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavouritesService {

    private final JuxBarUserService juxBarUserService;
    private final CocktailService cocktailService;
    private final SoftDrinkService softDrinkService;


    public Iterable<Cocktail> getFavouriteCocktails(Principal principal) {
        log.info("Received favCocktails request for user: {}", principal.getName());
        String username = principal.getName();

        JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);

        return juxBarUser.getFavourite_cocktails();
    }

    public ResponseEntity<String> addFavoriteCocktail(Integer id, Principal principal) {
        try {
            log.info("in the addFav with id : " + id);
            String username = principal.getName();
            log.info(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            Optional<Cocktail> favToAdd = cocktailService.getDrink(id);
            juxBarUser.addFav(favToAdd.get());
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("ajout OK");
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    public ResponseEntity<String> removeFavoriteCocktail(Integer id, Principal principal) {
        try {
            log.info("in the rmFav with id : " + id);
            String username = principal.getName();
            log.info(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            Optional<Cocktail> favToRemove = cocktailService.getDrink(id);
            juxBarUser.rmFav(favToRemove.get());
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("deletion OK");
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }

    }

    public Iterable<SoftDrink> getFavouriteSoftDrinks(Principal principal) {
        log.info("Received favSoftDrinks request for user: {}", principal.getName());
        String username = principal.getName();
        JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
        return juxBarUser.getFavourite_softdrinks();
    }

    public ResponseEntity<String> addFavoriteSoftDrink(Integer id, Principal principal) {
        try {
            log.info("in the addFav with id : " + id);
            String username = principal.getName();
            log.info(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            Optional<SoftDrink> favToAdd = softDrinkService.getDrink(id);
            juxBarUser.addFav(favToAdd.get());
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("ajout OK");
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

    public ResponseEntity<String> removeFavoriteSoftDrink(Integer id, Principal principal) {
        try {
            log.info("in the rmFav with id : " + id);
            String username = principal.getName();
            log.info(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            Optional<SoftDrink> favToRemove = softDrinkService.getDrink(id);
            juxBarUser.rmFav(favToRemove.get());
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("deletion OK");
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

}