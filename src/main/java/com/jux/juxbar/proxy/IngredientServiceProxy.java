package com.jux.juxbar.proxy;

import com.jux.juxbar.interfaces.IngredientServiceInterface;
import com.jux.juxbar.model.Ingredient;
import com.jux.juxbar.model.IngredientImage;
import com.jux.juxbar.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceProxy extends Thread implements IngredientServiceInterface {

    private final IngredientService ingredientService;
    private Iterable<Ingredient> proxyIngredients;
    private List<String> proxyIngredientNames;

    @Override
    public Iterable<Ingredient> getAllIngredients() {
        if (proxyIngredients == null) {
            log.info("getAllIngredients called");
            proxyIngredients = ingredientService.getAllIngredients();
        }
        return proxyIngredients;
    }

    @Override
    public Iterable<Ingredient> getAllIngredients(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Ingredient> getIngredient(int id) {
        return Optional.empty();
    }

    @Override
    public void saveIngredient(Ingredient ingredient) {

    }

    @Override
    public Optional<Ingredient> getIngredientByIdIngredient(String idIngredient) {
        return Optional.empty();
    }

    @Override
    public Optional<Ingredient> getIngredientByName(String strIngredient) {
        return Optional.empty();
    }

    @Override
    public List<String> getIngredientsStrings() {
        if(proxyIngredientNames == null) {
            proxyIngredientNames = ingredientService.getIngredientsStrings();
        }
        return proxyIngredientNames;
     }

    @Override
    public ResponseEntity<byte[]> getImage(String strIngredient) {
    return null;
    }

    @Override
    public ResponseEntity<byte[]> getPreview(String strIngredient) {
        return null;
    }
}
