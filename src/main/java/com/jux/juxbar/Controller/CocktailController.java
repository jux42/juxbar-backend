package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Repository.CocktailRepository;
import com.jux.juxbar.Service.CocktailService;
import com.jux.juxbar.Service.ImageCompressor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
public class CocktailController {

    @Autowired
    CocktailService cocktailService;
    @Autowired
    CocktailRepository cocktailRepository;
    @Autowired
    ImageCompressor imageCompressor;

    @GetMapping("/cocktails")
    public ResponseEntity<?> getCocktails(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {

        if (page != null && limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            return ResponseEntity.ok(cocktailService.getCocktails(pageable));
        } else {
            return ResponseEntity.ok(cocktailService.getAllCocktails());
        }
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
    public String saveCocktails() throws InterruptedException {
        return cocktailService.checkUpdate();

    }


    @GetMapping("cocktails/saveimages")
    public String saveCocktailsImages() {
        return cocktailService.saveCocktailsImages();
    }

    @GetMapping("cocktails/savepreviews")
    public String saveCocktailsPreviews() {
        return cocktailService.saveCocktailsPreviews();
    }


}