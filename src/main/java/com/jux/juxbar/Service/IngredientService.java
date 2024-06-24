package com.jux.juxbar.Service;

import com.jux.juxbar.Model.Ingredient;
import com.jux.juxbar.Model.IngredientResponse;
import com.jux.juxbar.Repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class IngredientService extends Thread {
    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ImageCompressor imageCompressor;


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

    public Optional<Ingredient> getIngredientByName(String strIngredient) {
        return ingredientRepository.findByStrIngredient(strIngredient);
    }

    public ArrayList<String> getIngredientsStrings() {
        Iterable<Ingredient> ingredients = this.getIngredients();
        ArrayList<String> ingredientsStrings = new ArrayList<>();
        ingredients.forEach(ingredient -> ingredientsStrings.add(ingredient.getStrIngredient()));
        return ingredientsStrings;

    }


    public void checkUpdate() throws InterruptedException {
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
            } catch (Exception e) {
                continue;
            }
            counter++;
            sleep(300);
        }
    }

    public ResponseEntity<byte[]> getImage(String strDescription) {
        System.out.println("in the getImage");
        Ingredient ingredient = this.getIngredientByName(strDescription).get();
        System.out.println(ingredient.getStrIngredient());

        try {
            byte[] compressed = imageCompressor.compress(ingredient.getImageData(), "png");
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(compressed);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<byte[]> getSmallImage(String strDescription) {
        return this.getIngredientByName(strDescription)
                .map(ingredient -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(ingredient.getSmallImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    public void saveIngredientsImages() {

        Iterable<Ingredient> ingredients = this.getIngredients();
        ingredients.forEach(ingredient -> {
            if (this.getIngredient(ingredient.getId()).get().getImageData() == null) {
                String Url = "https://www.thecocktaildb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                ingredient.setImageData(imageBytes);
                this.saveIngredient(ingredient);
                System.out.println("ONE MORE");
            }
        });
    }

    public void saveIngredientsSmallImages() {

        Iterable<Ingredient> ingredients = this.getIngredients();
        ingredients.forEach(ingredient -> {
            if (this.getIngredient(ingredient.getId()).get().getSmallImageData() == null) {
                String Url = "https://www.thecocktaildb.com/images/ingredients/" + ingredient.getStrIngredient() + "-Medium.png";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                ingredient.setSmallImageData(imageBytes);
                this.saveIngredient(ingredient);
                System.out.println("ONE MORE");
            }
        });
    }
}