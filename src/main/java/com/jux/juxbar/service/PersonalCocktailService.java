package com.jux.juxbar.service;

import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.repository.JuxBarUserRepository;
import com.jux.juxbar.repository.PersonalCocktailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor

public class PersonalCocktailService {

    private final PersonalCocktailRepository personalCocktailRepository;
    private final RestTemplate restTemplate;
    private final JuxBarUserRepository juxBarUserRepository;


    public Iterable<PersonalCocktail> getPersonalCocktails(String ownerName) {
        log.info(ownerName);
        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(ownerName);
        return personalCocktailRepository.findByOwnerName(juxBarUser.getUsername());

    }

    public String savePersonalCocktail(PersonalCocktail personalCocktail) {
        String url = personalCocktail.getStrDrinkThumb();
        byte[] imageBytes = restTemplate.getForObject(
                url, byte[].class);
        personalCocktail.setImageData(imageBytes);
        log.info("personal ===== " + personalCocktail);
        log.info("ONE MORE");

        personalCocktailRepository.save(personalCocktail);
        return "Saved";
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
}
