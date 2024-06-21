package com.jux.juxbar.Service;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.CocktailResponse;
import com.jux.juxbar.Repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CocktailService extends Thread{
    @Autowired
    CocktailRepository cocktailRepository;

    @Autowired
    private RestTemplate restTemplate;

    public String checkUpdate(List<Cocktail> cocktails) throws InterruptedException {

        int counter = 0;
        for (Cocktail cocktail : cocktails) {
            Optional<Cocktail> existingCocktail = this.getCocktailByIdDrink(cocktail.getIdDrink());
            if (existingCocktail.isPresent())
            {
                System.out.println("doublon");
            }else{
                ResponseEntity<CocktailResponse> oneResponse = restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/lookup.php?i=" + cocktail.getIdDrink(),
                        CocktailResponse.class);
                CocktailResponse oneCocktailResponse = oneResponse.getBody();
                assert oneCocktailResponse != null;
                List<Cocktail> newCocktails = oneCocktailResponse.getDrinks();
                newCocktails.forEach(this::saveCocktail);

                counter++;
                sleep(300);

            }
        }
        return counter == 0 ? "pas de mise à jour"
                : "mise à jour des cocktails effectuée, "+counter+" cocktails ajoutés" ;
    }

    public Optional<Cocktail> getCocktailByIdDrink(String idDrink){
        return cocktailRepository.findByIdDrink(idDrink);
    }
    public void saveCocktail(Cocktail cocktail) {
        cocktailRepository.save(cocktail);

    }

    public Optional<Cocktail> getCocktail(int id) {
        return cocktailRepository.findById(id);
    }

    public Iterable<Cocktail> getAllCocktails() {
        return cocktailRepository.findAll();
    }
    public Page<Cocktail> getCocktails(Pageable pageable) {
        return cocktailRepository.findAll(pageable);
    }



}
