package com.jux.juxbar.Service;

import com.jux.juxbar.Model.Ingredient;
import com.jux.juxbar.Model.IngredientResponse;
import com.jux.juxbar.Repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class IngredientService extends Thread {
    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    private RestTemplate restTemplate;


    public Iterable<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> getIngredient(int id) {
        return ingredientRepository.findById(id);
    }

    public void saveIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);

    }

    public Optional<Ingredient> getIngredientByIdIngredient(String idIngredient) {
        return ingredientRepository.findByIdIngredient(idIngredient);
    }

    public Optional<Ingredient> getIngredientByName(String strIngredient){
        return ingredientRepository.findByStrIngredient(strIngredient);
    }


    public String checkUpdate() throws InterruptedException {
        int counter = 0;


        for (int i = 1; i < 617; i++) {


            try {

                ResponseEntity<IngredientResponse> response =
                        restTemplate.getForEntity(
                                "https://www.thecocktaildb.com/api/json/v2/9973533/lookup.php?iid=" + i,
                                IngredientResponse.class);
                IngredientResponse ingredientResponse = response.getBody();


                assert ingredientResponse != null;
                List<Ingredient> ingredients = ingredientResponse.getIngredients();


                for (Ingredient ingredient : ingredients) {
                    Optional<Ingredient> thatIngredient = this.getIngredientByIdIngredient(ingredient.getIdIngredient());
                    if (thatIngredient.isPresent()) continue;
                    this.saveIngredient(ingredient);
                }
            } catch (Exception e){
                continue;
            }

                counter++;
                sleep(300);
            }


        return counter == 0 ? "pas de mise à jour"
                : "mise à jour des ingrédients effectuée";

    }
}

