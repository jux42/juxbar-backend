package com.jux.juxbar.service;

import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.model.Ingredient;
import com.jux.juxbar.repository.IngredientRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
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

    @PostConstruct
    @Cacheable("ingredients")
    public void initCache(){
        log.info("initializing ingredients cache...");
        ingredientRepository.findAll();
        log.info("cache initialized !");
    }


    public Iterable<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Cacheable("ingredients")
    public Iterable<Ingredient> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
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
        Iterable<Ingredient> ingredients = this.getAllIngredients();
        List<String> ingredientsStrings = new ArrayList<>();
        ingredients.forEach(ingredient -> ingredientsStrings.add(ingredient.getStrIngredient()));
        return ingredientsStrings;

    }



    public ResponseEntity<byte[]> getImage(String strIngredient) {
        log.info("in the getImage");
        Ingredient ingredient = this.getIngredientByName(strIngredient).get();
        log.info(ingredient.getStrIngredient());

        try {
            byte[] compressed = imageCompressor.compress(ingredient.getImageData().getImage(), "png", 0.4);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(compressed);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<byte[]> getPreview(String strIngredient) {
        Ingredient ingredient = this.getIngredientByName(strIngredient).get();
        try {
            byte[] compressedPreview = imageCompressor.compress(ingredient.getImageData().getPreview(), "png", 0.2);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(compressedPreview);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }




}