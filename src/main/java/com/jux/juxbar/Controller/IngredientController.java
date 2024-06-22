package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Ingredient;
import com.jux.juxbar.Repository.IngredientRepository;
import com.jux.juxbar.Service.ImageCompressor;
import com.jux.juxbar.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;


@RestController
public class IngredientController extends Thread {


    @Autowired
    IngredientService ingredientService;
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
    public Optional<Ingredient> getIngredientByName(@PathVariable String strDescription) {
        return ingredientService.getIngredientByName(strDescription);
    }

    @GetMapping("/ingredient/{id}")
    public Optional<Ingredient> getIngredientByName(@PathVariable int id) {
        return ingredientService.getIngredient(id);
    }

    @GetMapping("/ingredients")
    public Iterable<Ingredient> getIngredients() {
        return ingredientService.getIngredients();
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
    public String saveIngredientsImages() {
        return ingredientService.saveIngredientsImages();
    }

    @GetMapping("ingredients/savesmallimages")
    public String saveIngredientsSmallImages() {
        return ingredientService.saveIngredientsSmallImages();
    }

    @GetMapping("/ingredients/strings")
    public ArrayList<String> getIngredientsStrings() {
        return ingredientService.getIngredientsStrings();

    }
}