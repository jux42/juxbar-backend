package com.jux.juxbar.service;

import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.repository.JuxBarUserRepository;
import com.jux.juxbar.repository.PersonalCocktailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jux.juxbar.model.State.SHOWED;
import static com.jux.juxbar.model.State.TRASHED;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOperationsService {


    private final JuxBarUserRepository juxBarUserRepository;
    private final PersonalCocktailRepository personalCocktailRepository;

    public List<Integer> listTrashedCocktailsOfUser (String username) {

        List<PersonalCocktail> trashedPersonalCocktails = personalCocktailRepository.findByOwnerNameAndState(username, TRASHED);
        List<Integer> cocktailIdList = new ArrayList<>();
        trashedPersonalCocktails.forEach(cocktail->{
            cocktailIdList.add(cocktail.getId());
        });

        return cocktailIdList;
    }

    public String restoreOneTrashedCocktail(String username, Integer id) {
        try{
            Optional<PersonalCocktail> trashedCocktail = personalCocktailRepository.findById(id);
            if (trashedCocktail.isPresent() && trashedCocktail.get().getState() == TRASHED) {
                PersonalCocktail cocktail = trashedCocktail.get();
                cocktail.setState(SHOWED);
                personalCocktailRepository.save(cocktail);
                return "cocktail successfully restored";
            }
            else return "this cocktail was not found in the trashcan";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String restoreAllCocktailsOfUser(String username) {
        List<PersonalCocktail> allTrashed = personalCocktailRepository.findByOwnerNameAndState(username, TRASHED);

        if(allTrashed.isEmpty()){
            return "no cocktail found in the trashcan";
        }

        allTrashed.forEach(cocktail -> {
            cocktail.setState(SHOWED);
            personalCocktailRepository.save(cocktail);
        });
        return "all cocktails successfully restored";
    }

}
