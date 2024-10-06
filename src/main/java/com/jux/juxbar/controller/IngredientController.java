package com.jux.juxbar.controller;

import com.jux.juxbar.component.IngredientApiInteractor;
import com.jux.juxbar.model.Ingredient;
import com.jux.juxbar.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/ingredient/name/{strIngredient}")
    public Optional<Ingredient> getIngredientByName(@PathVariable String strIngredient) {
        return ingredientService.getIngredientByName(strIngredient);
    }

    @GetMapping("/ingredient/{id}")
    public Optional<Ingredient> getIngredientByName(@PathVariable int id) {
        return ingredientService.getIngredient(id);
    }

    @Async("taskExecutor")
    @GetMapping("/ingredients")
    public CompletableFuture<Iterable<Ingredient>> getIngredients(@RequestParam(value = "page", required = false) Integer page,
                                                                  @RequestParam(value = "limit", required = false) Integer limit) {



        return CompletableFuture.supplyAsync(() -> {
            try {
                if (page != null && limit != null) {
                    Pageable pageable = PageRequest.of(page, limit);
                    return ingredientService.getIngredients(pageable);
                }
                return ingredientService.getAllIngredients();
            } catch (Exception ignored) {

            }

            return null;
        });
    }


    @GetMapping("/ingredient/{strIngredient}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String strIngredient) {

        return ingredientService.getImage(strIngredient);

    }

    @GetMapping("/ingredient/{strIngredient}/smallimage")
    public ResponseEntity<byte[]> getPreview(@PathVariable String strIngredient) {
        return ingredientService.getPreview(strIngredient);

    }

    @GetMapping("ingredients/downloadimages")
    public ResponseEntity<String> downloadIngredientsImages() {
        ingredientApiInteractor.downloadImages();
        return ResponseEntity.ok("Images Ingrédients à jour");
    }

    @GetMapping("ingredients/downloadpreviews")
    public ResponseEntity<String> downloadIngredientPreviews() {
        ingredientApiInteractor.downloadPreviews();
        return ResponseEntity.ok("Previews Ingrédients à jour");

    }

    @GetMapping("/ingredients/strings")
    public List<String> getIngredientsStrings() {
        return ingredientService.getIngredientsStrings();

    }
}