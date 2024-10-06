package com.jux.juxbar.controller;

import com.jux.juxbar.component.CocktailApiInteractor;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.service.CocktailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CocktailController {


    private final CocktailService cocktailService;
    private final CocktailApiInteractor cocktailApiInteractor;


    @GetMapping("/cocktails")
    public ResponseEntity<Iterable<Cocktail>> getCocktails(

            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {


        if (page != null && limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            return ResponseEntity.ok(cocktailService.getDrinks(pageable));
        }
        return ResponseEntity.ok(cocktailService.getAllDrinks());

    }


    @GetMapping("/cocktail/{id}")
    public Optional<Cocktail> getCocktail(@PathVariable int id) {
        return cocktailService.getDrink(id);
    }

    @GetMapping("/cocktail/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {

        byte[] imageBytes = cocktailService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "public, max-age=31536000");
        headers.add("Content-Type", "image/jpeg");

        return new  ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/cocktail/{id}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable int id) {
        byte[] imageBytes = cocktailService.getPreview(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "public, max-age=31536000");
        headers.add("Content-Type", "image/jpeg");

        return new  ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/cocktails/download")
    public ResponseEntity<String> downloadCocktails() throws InterruptedException {
        cocktailApiInteractor.checkUpdateAndDownload();
        return ResponseEntity.ok("Cocktails à jour");

    }


    @GetMapping("cocktails/downloadimages")
    public ResponseEntity<String> downloadCocktailsImages() {
        cocktailApiInteractor.downloadImages();
        return ResponseEntity.ok("Images Cocktails à jour");

    }

    @GetMapping("cocktails/downloadpreviews")
    public ResponseEntity<String> downloadCocktailsPreviews() {
        cocktailApiInteractor.downloadPreviews();
        return ResponseEntity.ok("Previews Cocktails à jour");

    }

    @GetMapping("cocktails/arraysize")
    public ResponseEntity<Long> getCocktailsArraySize() {
        return ResponseEntity.ok(cocktailService.getAllDrinks().spliterator().getExactSizeIfKnown());
    }


}