package com.jux.juxbar.Service;

import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Repository.CocktailRepository;
import com.jux.juxbar.Repository.JuxBarUserRepository;
import com.jux.juxbar.Repository.SoftDrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouritesService {

    @Autowired
    CocktailRepository cocktailRepository;
    @Autowired
    SoftDrinkRepository softDrinkRepository;
    @Autowired
    JuxBarUserRepository juxBarUserRepository;


    public List<Integer> getfavouriteCocktails(String userName){
        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(userName);
        return juxBarUser.getFavouriteCocktails();
    }

    public List<Integer> getFavouriteSoftDrinks(String userName){
        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(userName);
        return juxBarUser.getFavouriteSoftDrinks();
    }

}
