package com.jux.juxbar.interfaces;

import com.jux.juxbar.model.Ingredient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IngredientServiceInterface {


    Iterable<Ingredient> getAllIngredients();

    Iterable<Ingredient> getAllIngredients(Pageable pageable);

    Optional<Ingredient> getIngredient(int id);

    void saveIngredient(Ingredient ingredient);

    Optional<Ingredient> getIngredientByIdIngredient(String idIngredient);

    Optional<Ingredient> getIngredientByName(String strIngredient);

    List<String> getIngredientsStrings();

    ResponseEntity<byte[]> getImage(String strIngredient);

    ResponseEntity<byte[]> getPreview(String strIngredient);
}
