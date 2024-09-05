package com.jux.juxbar.Service;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Model.SoftDrink;
import com.jux.juxbar.Repository.CocktailRepository;
import com.jux.juxbar.Repository.JuxBarUserRepository;
import com.jux.juxbar.Repository.SoftDrinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class FavouritesService {

    @Autowired
    CocktailRepository cocktailRepository;
    @Autowired
    SoftDrinkRepository softDrinkRepository;
    @Autowired
    JuxBarUserRepository juxBarUserRepository;
    @Autowired
    JuxBarUserService juxBarUserService;
    @Autowired
    CocktailService cocktailService;
    @Autowired
    SoftDrinkService softDrinkService;


    public Iterable<Cocktail> getFavouriteCocktails(Principal principal) {
        log.info("Received favCocktails request for user: " + principal.getName());
        String username = principal.getName();

        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(username);

        return juxBarUser.getFavourite_cocktails();
    }

    public ResponseEntity<String> addFavoriteCocktail(Integer id, Principal principal) {
        try {
            log.info("in the addFav with id : " + id);
            String username = principal.getName();
            log.info(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            Optional<Cocktail> favToAdd = cocktailService.getCocktail(id);
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
            Optional<Cocktail> favToRemove = cocktailService.getCocktail(id);
            juxBarUser.rmFav(favToRemove.get());
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("deletion OK");
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }

    }

    public Iterable<SoftDrink> getFavouriteSoftDrinks(Principal principal) {
        log.info("Received favSoftDrinks request for user: " + principal.getName());
        String username = principal.getName();
        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(username);
        return juxBarUser.getFavourite_softdrinks();
    }

    public ResponseEntity<String> addFavoriteSoftDrink(Integer id, Principal principal) {
        try {
            log.info("in the addFav with id : " + id);
            String username = principal.getName();
            log.info(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            Optional<SoftDrink> favToAdd = softDrinkService.getSoftDrink(id);
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
            Optional<SoftDrink> favToRemove = softDrinkService.getSoftDrink(id);
            juxBarUser.rmFav(favToRemove.get());
            juxBarUserService.saveJuxBarUser(juxBarUser);
            return ResponseEntity.ok().body("deletion OK");
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }
    }

}