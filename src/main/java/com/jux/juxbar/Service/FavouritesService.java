package com.jux.juxbar.Service;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Model.SoftDrink;
import com.jux.juxbar.Repository.CocktailRepository;
import com.jux.juxbar.Repository.JuxBarUserRepository;
import com.jux.juxbar.Repository.SoftDrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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


    public List<Integer> getfavouriteCocktails(String userName){
        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(userName);
        return juxBarUser.getFavouriteCocktails();
    }

    public List<Integer> getFavouriteSoftDrinks(String userName){
        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(userName);
        return juxBarUser.getFavouriteSoftDrinks();
    }

    public Iterable<Cocktail> getFavouriteCocktails(Principal principal) {
        System.out.println("Received favCocktails request for user: " + principal.getName());
        String username = principal.getName();

        List<Integer> cocktailIds = this.getfavouriteCocktails(username);
        System.out.println(cocktailIds.toString());
        ArrayList<Cocktail> favouriteCocktails = new ArrayList<>();
        cocktailIds.forEach(cocktailId -> {
                    Optional<Cocktail> newFav = cocktailService.getCocktail(cocktailId);
                    newFav.ifPresent(cocktail -> favouriteCocktails.add(newFav.get()));
                }
        );
        return favouriteCocktails;
    }

    public ResponseEntity<String> addFavoriteCocktail(Integer id, Principal principal){
        try {
            System.out.println("in the addFav with id : " + id);
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteCocktails());
            List<Integer> cocktailIds = this.getfavouriteCocktails(username);
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

    public ResponseEntity<String> removeFavoriteCocktail(Integer id, Principal principal){
        try {
            System.out.println("in the rmFav with id : " + id);
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteCocktails());
            List<Integer> cocktailIds = this.getfavouriteCocktails(username);
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

    public Iterable<SoftDrink> getFavouriteSoftDrinks(Principal principal) {
        System.out.println("Received favSoftDrinks request for user: " + principal.getName());
        String username = principal.getName();

        List<Integer> softDrinksIds = this.getFavouriteSoftDrinks(username);
        System.out.println(softDrinksIds.toString());
        ArrayList<SoftDrink> favouriteSoftDrinks = new ArrayList<>();
        softDrinksIds.forEach(softDrinkId -> {
                    Optional<SoftDrink> newFav = softDrinkService.getSoftDrink(softDrinkId);
                    newFav.ifPresent(softDrink -> favouriteSoftDrinks.add(newFav.get()));
                }
        );
        return favouriteSoftDrinks;
    }

    public ResponseEntity<String> addFavoriteSoftDrink(Integer id, Principal principal){
        try {
            System.out.println("in the addFav soft with id : " + id);
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteSoftDrinks());
            List<Integer> softDrinksIds = this.getFavouriteSoftDrinks(username);
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

    public ResponseEntity<String> removeFavoriteSoftDrink(Integer id, Principal principal){
        try {
            System.out.println("in the rmFav soft with id : " + id);
            String username = principal.getName();
            System.out.println(username);
            JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
            System.out.println(juxBarUser.getFavouriteSoftDrinks());
            List<Integer> softDrinksIds = this.getFavouriteSoftDrinks(username);
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
