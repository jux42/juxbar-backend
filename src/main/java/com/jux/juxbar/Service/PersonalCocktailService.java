package com.jux.juxbar.Service;

import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Model.PersonalCocktail;
import com.jux.juxbar.Repository.JuxBarUserRepository;
import com.jux.juxbar.Repository.PersonalCocktailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class PersonalCocktailService {

    @Autowired
    PersonalCocktailRepository personalCocktailRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JuxBarUserRepository juxBarUserRepository;


//    public Optional<PersonalCocktail> getPersonalCocktail(int id,  String userName) {
//        return Objects.equals(personalCocktailRepository.findById(id)
//                .get()
//                .getOwnerName(), userName)
//                        ? personalCocktailRepository.findById(id)
//                        : Optional.empty();
//        };


    public Iterable<PersonalCocktail> getPersonalCocktails(String ownerName) {
        log.info(ownerName);
        JuxBarUser juxBarUser = juxBarUserRepository.findByUsername(ownerName);
        return personalCocktailRepository.findByOwnerName(juxBarUser.getUsername());

    }

    public String savePersonalCocktail(PersonalCocktail personalCocktail) {
        String Url = personalCocktail.getStrDrinkThumb();
        byte[] imageBytes = restTemplate.getForObject(
                Url, byte[].class);
        personalCocktail.setImageData(imageBytes);
        log.info("personal ===== "+ personalCocktail);
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
        //TODO try/catch
    }
}
