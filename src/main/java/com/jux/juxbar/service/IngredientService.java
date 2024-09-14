package com.jux.juxbar.service;

import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.model.Ingredient;
import com.jux.juxbar.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService extends Thread {

    private final IngredientRepository ingredientRepository;
    private final ImageCompressor imageCompressor;

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

    public List<String> getIngredientsStrings() {
        Iterable<Ingredient> ingredients = this.getIngredients();
        List<String> ingredientsStrings = new ArrayList<>();
        ingredients.forEach(ingredient -> ingredientsStrings.add(ingredient.getStrIngredient()));
        return ingredientsStrings;

    }



    public ResponseEntity<byte[]> getImage(String strIngredient) {
        log.info("in the getImage");
        Ingredient ingredient = this.getIngredientByName(strIngredient).get();
        log.info(ingredient.getStrIngredient());

        try {
            byte[] compressed = imageCompressor.compress(ingredient.getImageData(), "png");
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(compressed);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<byte[]> getSmallImage(String strIngredient) {
        return this.getIngredientByName(strIngredient)
                .map(ingredient -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(ingredient.getSmallImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }




}