package com.jux.juxbar.controller;

import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.service.SoftDrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SoftDrinkController {

    private final SoftDrinkService softDrinkService;

    @GetMapping("/softdrinks")
    public Iterable<SoftDrink> getSoftDrinks() {

        return softDrinkService.getSoftDrinks();
    }

    @GetMapping("/softdrink/{id}")
    public Optional<SoftDrink> getSoftDrink(@PathVariable int id) {
        return softDrinkService.getSoftDrink(id);
    }

    @GetMapping("softdrink/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        return softDrinkService.getImage(id);
    }

    @GetMapping("softdrink/{id}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable int id) {
        return softDrinkService.getPreview(id);
    }

    @GetMapping("/softdrinks/save")
    public ResponseEntity<String> saveSoftDrinks() throws InterruptedException {

        softDrinkService.checkUpdate();
        return ResponseEntity.ok("Soft drinks à jour");

    }


    @GetMapping("softDrinks/saveimages")
    public ResponseEntity<String> saveSoftDrinksImages() {

        softDrinkService.saveSoftDrinksImages();
        return ResponseEntity.ok("Images Soft à jour");
    }

    @GetMapping("softdrinks/savepreviews")
    public ResponseEntity<String> saveCocktailsPreviews() {

        softDrinkService.saveCocktailsPreviews();
        return ResponseEntity.ok("Previews Soft à jour");

    }
}