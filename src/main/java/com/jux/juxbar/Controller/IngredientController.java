package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Ingredient;
import com.jux.juxbar.Repository.IngredientRepository;
import com.jux.juxbar.Service.ImageCompressor;
import com.jux.juxbar.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


@RestController
public class IngredientController{


    @Autowired
    IngredientService ingredientService;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    ImageCompressor imageCompressor;
    private final Executor taskExecutor;

    public IngredientController( @Qualifier("taskExecutor") Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }


    @GetMapping("/ingredients/save")
    public ResponseEntity<String> saveIngredients() throws InterruptedException {

        ingredientService.checkUpdate();
        return ResponseEntity.ok("Ingredients à jour");
    }

    @GetMapping("/ingredient/name/{strDescription}")
    public Optional<Ingredient> getIngredientByName(@PathVariable String strDescription) {
        return ingredientService.getIngredientByName(strDescription);
    }

    @GetMapping("/ingredient/{id}")
    public Optional<Ingredient> getIngredientByName(@PathVariable int id) {
        return ingredientService.getIngredient(id);
    }

    @Async("taskExecutor")
    @GetMapping("/ingredients")
    public CompletableFuture<Iterable<Ingredient>> getIngredients() {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Iterable<Ingredient> ingredients = ingredientService.getIngredients();
                return ingredients;
            } catch (Exception ignored) {

            }

            return null;
        });
    }


    @GetMapping("/ingredient/{strDescription}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String strDescription) {

        return ingredientService.getImage(strDescription);

    }

    @GetMapping("/ingredient/{strDescription}/smallimage")
    public ResponseEntity<byte[]> getSmallImage(@PathVariable String strDescription) {
        return ingredientService.getSmallImage(strDescription);

    }

    @GetMapping("ingredients/saveimages")
    public ResponseEntity<String> saveIngredientsImages() {
        ingredientService.saveIngredientsImages();
        return ResponseEntity.ok("Images Ingrédients à jour");
    }

    @GetMapping("ingredients/savesmallimages")
    public ResponseEntity<String> saveIngredientsSmallImages() {
        ingredientService.saveIngredientsSmallImages();
        return ResponseEntity.ok("SmallImages Ingrédients à jour");

    }

    @GetMapping("/ingredients/strings")
    public ArrayList<String> getIngredientsStrings() {
        return ingredientService.getIngredientsStrings();

    }
}