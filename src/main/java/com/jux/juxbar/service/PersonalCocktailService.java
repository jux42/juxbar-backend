package com.jux.juxbar.service;

import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.model.PersonalCocktailImage;
import com.jux.juxbar.model.State;
import com.jux.juxbar.repository.JuxBarUserRepository;
import com.jux.juxbar.repository.PersonalCocktailImageRepository;
import com.jux.juxbar.repository.PersonalCocktailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor

public class PersonalCocktailService {

    private final PersonalCocktailRepository personalCocktailRepository;
    private final RestTemplate restTemplate;
    private final JuxBarUserRepository juxBarUserRepository;
    private final PersonalCocktailImageRepository personalCocktailImageRepository;


    public Iterable<PersonalCocktail> getPersonalCocktails(String ownerName) {
        log.info(ownerName);
        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(ownerName);
        return personalCocktailRepository.findByOwnerName(juxBarUser.getUsername());

    }

    public String savePersonalCocktail(PersonalCocktail personalCocktail) {
//        String url = personalCocktail.getStrDrinkThumb();
        personalCocktail.setState(State.SHOWED);
        System.out.println(personalCocktail);
        log.info("personal ===== {}", personalCocktail);
        log.info("ONE MORE");

        personalCocktailRepository.save(personalCocktail);

        return "Saved";
    }

    public String  savePersonalCocktailImage(String cocktailName, byte[] picture){

        Optional<PersonalCocktail> personalCocktail = personalCocktailRepository.findByStrDrink(cocktailName);
        PersonalCocktailImage image = new PersonalCocktailImage();
        image.setImage(picture);
        personalCocktail.get().setImageData(image);
        personalCocktailRepository.save(personalCocktail.get());
        return "custom cocktail picture updated";
    }

    public PersonalCocktail getPersonalCocktail(int id, String userName) {
        AtomicReference<PersonalCocktail> personalCocktail = new AtomicReference<>();
        Iterable<PersonalCocktail> personalCocktails = this.personalCocktailRepository
                .findByOwnerName(userName);
        personalCocktails.forEach(pc -> {
            if (pc.getId() == id) {
                personalCocktail.set(pc);
            }
        });
        log.info(String.valueOf(personalCocktail));
        return personalCocktail.get();
    }


    public byte[] getPersonalCocktailImage(int id){

        Optional<PersonalCocktail> personalCocktail = personalCocktailRepository.findById(id);
        if (personalCocktail.isPresent() && personalCocktail.get().getImageData() != null) {
            return personalCocktail.get().getImageData().getImage();
        }
       else return null;

    }

    public String removePersonalCocktail(int id, String userName) {
        Iterable<PersonalCocktail> personalCocktails = this.personalCocktailRepository
                .findByOwnerName(userName);
        personalCocktails.forEach(pc -> {
            if (pc.getId() == id) {
                this.personalCocktailRepository.delete(pc);
            }
        });
        return "suppression effectuée";
    }

    public String trashPersonalCocktail(int id, String userName) {
        Iterable<PersonalCocktail> personalCocktails = this.personalCocktailRepository
                .findByOwnerName(userName);
        personalCocktails.forEach(pc -> {
            if (pc.getId() == id) {
                pc.setState(State.TRASHED);
                this.personalCocktailRepository.save(pc);
            }
        });
        return "mise en corbeille effectuée";
    }
}
