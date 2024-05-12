package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Ingredient;
import com.jux.juxbar.Repository.IngredientRepository;
import com.jux.juxbar.Service.ImageCompressor;
import com.jux.juxbar.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@RestController
public class IngredientController extends Thread {


    @Autowired
    IngredientService ingredientService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    ImageCompressor imageCompressor;


    @GetMapping("/ingredients/save")
    public String saveIngredients() throws InterruptedException {

        ingredientService.checkUpdate();

        return "OK";
    }

    @GetMapping("/ingredient/name/{strDescription}")
    public Optional<Ingredient> getIngredientByName(@PathVariable String strDescription){
        return ingredientService.getIngredientByName(strDescription);
    }

    @GetMapping("/ingredient/{id}")
    public Optional<Ingredient> getIngredientByName(@PathVariable int id){
        return ingredientService.getIngredient(id);
    }

    @GetMapping("/ingredients")
    public Iterable<Ingredient> getIngredients(){
        return ingredientService.getIngredients();
    }


    @GetMapping("/ingredient/{strDescription}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String strDescription){
        System.out.println("in the getImage");
        Ingredient ingredient =  ingredientService.getIngredientByName(strDescription).get();
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

    @GetMapping("/ingredient/{strDescription}/smallimage")
    public ResponseEntity<byte[]> getSmallImage(@PathVariable String strDescription){
        return ingredientService.getIngredientByName(strDescription)
                .map(ingredient -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(ingredient.getSmallImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
    @GetMapping("ingredients/saveimages")
    public String saveIngredientsImages() {

        Iterable<Ingredient> ingredients = ingredientService.getIngredients();
        ingredients.forEach(ingredient -> {
            if (ingredientService.getIngredient(ingredient.getId()).get().getImageData() == null) {
                String Url = "https://www.thecocktaildb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                ingredient.setImageData(imageBytes);
                ingredientService.saveIngredient(ingredient);
                System.out.println("ONE MORE");
            }

        });
        return "images à jour";
    }
    @GetMapping("ingredients/savesmallimages")
    public String saveIngredientsSmallImages() {

        Iterable<Ingredient> ingredients = ingredientService.getIngredients();
        ingredients.forEach(ingredient -> {
            if (ingredientService.getIngredient(ingredient.getId()).get().getSmallImageData() == null) {
                String Url = "https://www.thecocktaildb.com/images/ingredients/" + ingredient.getStrIngredient() + "-Medium.png";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                ingredient.setSmallImageData(imageBytes);
                ingredientService.saveIngredient(ingredient);
                System.out.println("ONE MORE");
            }

        });
        return "images à jour";
    }
}


