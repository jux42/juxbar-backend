package com.jux.juxbar.Service;

import com.jux.juxbar.Model.PersonalCocktail;
import com.jux.juxbar.Repository.PersonalCocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalCocktailService {

    @Autowired
    PersonalCocktailRepository personalCocktailRepository;



//    public Optional<PersonalCocktail> getPersonalCocktail(int id,  String userName) {
//        return Objects.equals(personalCocktailRepository.findById(id)
//                .get()
//                .getOwnerName(), userName)
//                        ? personalCocktailRepository.findById(id)
//                        : Optional.empty();
//        };


    public Iterable<PersonalCocktail> getPersonalCocktails(String ownerName) {
        System.out.println(ownerName);
        return personalCocktailRepository.findAllByOwnerName(ownerName);

    }

    public String  savePersonalCocktail(PersonalCocktail personalCocktail){
        personalCocktailRepository.save(personalCocktail);
        return "Saved";
    }
}
