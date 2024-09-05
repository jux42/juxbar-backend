package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Service.CocktailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class CocktailController {

    @Autowired
    CocktailService cocktailService;


    @GetMapping("/cocktails")
    public ResponseEntity<Iterable<Cocktail>> getCocktails(

            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        if (page != null && limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            return ResponseEntity.ok(cocktailService.getCocktails(pageable));
        }
        return ResponseEntity.ok(cocktailService.getAllCocktails());

    }


    @GetMapping("/cocktail/{id}")
    public Optional<Cocktail> getCocktail(@PathVariable int id) {
        return cocktailService.getCocktail(id);
    }

    @GetMapping("/cocktail/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        return cocktailService.getImage(id);
    }

    @GetMapping("/cocktail/{id}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable int id) {
        return cocktailService.getPreview(id);
    }

    @GetMapping("/cocktails/save")
    public ResponseEntity<String> saveCocktails() throws InterruptedException {
        cocktailService.checkUpdate();
        return ResponseEntity.ok("Cocktails à jour");

    }


    @GetMapping("cocktails/saveimages")
    public ResponseEntity<String> saveCocktailsImages() {
        cocktailService.saveCocktailsImages();
        return ResponseEntity.ok("Images Cocktails à jour");

    }

    @GetMapping("cocktails/savepreviews")
    public ResponseEntity<String> saveCocktailsPreviews() {
        cocktailService.saveCocktailsPreviews();
        return ResponseEntity.ok("Previews Cocktails à jour");

    }


}