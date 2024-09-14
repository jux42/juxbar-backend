package com.jux.juxbar.controller;

import com.jux.juxbar.component.IngredientApiInteractor;
import com.jux.juxbar.model.Ingredient;
import com.jux.juxbar.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@RestController
@RequiredArgsConstructor
public class IngredientController {


    private final IngredientService ingredientService;
    private final IngredientApiInteractor ingredientApiInteractor;

    @GetMapping("/ingredients/download")
    public ResponseEntity<String> downloadIngredients() throws InterruptedException {

        ingredientApiInteractor.checkUpdateAndDownload();
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
                return ingredientService.getIngredients();
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

    @GetMapping("ingredients/downloadimages")
    public ResponseEntity<String> downloadIngredientsImages() {
        ingredientApiInteractor.downloadImages();
        return ResponseEntity.ok("Images Ingrédients à jour");
    }

    @GetMapping("ingredients/downloadpreviews")
    public ResponseEntity<String> downloadIngredientPreviews() {
        ingredientApiInteractor.downloadPreviews();
        return ResponseEntity.ok("SmallImages Ingrédients à jour");

    }

    @GetMapping("/ingredients/strings")
    public List<String> getIngredientsStrings() {
        return ingredientService.getIngredientsStrings();

    }
}