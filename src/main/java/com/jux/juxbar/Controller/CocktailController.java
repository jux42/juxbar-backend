package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.CocktailResponse;
import com.jux.juxbar.Repository.CocktailRepository;
import com.jux.juxbar.Service.CocktailService;
import com.jux.juxbar.Service.ImageCompressor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class CocktailController {

    @Autowired
    CocktailService cocktailService;
    @Autowired
    CocktailRepository cocktailRepository;
    @Autowired
    ImageCompressor imageCompressor;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/cocktails")
    public ResponseEntity<?> getCocktails(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {

        if (page != null && limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Cocktail> pagedResult = cocktailService.getCocktails(pageable);
            return ResponseEntity.ok(pagedResult);
        } else {
            Iterable<Cocktail> allCocktails = cocktailService.getAllCocktails();
            return ResponseEntity.ok(allCocktails);
        }
    }

    @GetMapping("/cocktail/{id}")
    public Optional<Cocktail> getCocktail(@PathVariable int id) {
        return cocktailService.getCocktail(id);
    }

    @GetMapping("/cocktail/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        return cocktailService.getCocktail(id)
                .map(cocktail -> {
                    try {
                        byte[] compressed = imageCompressor.compress(cocktail.getImageData(), "jpg");
                        return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(compressed);
                    } catch (Exception e) {
                        return ResponseEntity.internalServerError().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cocktail/{id}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable int id) {
        return cocktailService.getCocktail(id)
                .map(cocktail -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(cocktail.getPreview()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/cocktails/save")
    public String saveCocktails() throws InterruptedException {
        ResponseEntity<CocktailResponse> response =
                restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?a=Alcoholic",
                        CocktailResponse.class);
        CocktailResponse cocktailResponse = response.getBody();
        if (cocktailResponse == null) throw new InterruptedException();
        List<Cocktail> cocktails = cocktailResponse.getDrinks();

        return cocktailService.checkUpdate(cocktails);

    }


    @GetMapping("cocktails/saveimages")
    public String saveCocktailsImages() {
        AtomicInteger counter = new AtomicInteger(0);

        Iterable<Cocktail> cocktails = cocktailService.getAllCocktails();
        cocktails.forEach(cocktail -> {
            if (cocktailService.getCocktail(cocktail.getId()).get().getImageData() == null) {
                String Url = cocktail.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                cocktail.setImageData(imageBytes);
                cocktailService.saveCocktail(cocktail);
                counter.getAndIncrement();

            }

        });
        return counter.get() == 0 ? "pas de nouvelles images"
                :  "Nombre d'images ajoutées : " + counter.get();
    }

    @GetMapping("cocktails/savepreviews")
    public String saveCocktailsPreviews() {

        AtomicInteger counter = new AtomicInteger(0);
        Iterable<Cocktail> cocktails = cocktailService.getAllCocktails();
        cocktails.forEach(cocktail -> {
            if (cocktailService.getCocktail(cocktail.getId()).get().getPreview() == null) {
                String Url = cocktail.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                cocktail.setPreview(imageBytes);
                cocktailService.saveCocktail(cocktail);
                counter.getAndIncrement();
            }

        });

        return counter.get() == 0 ? "pas de nouvelle preview"
                :  "Nombre de previews ajoutées : " + counter.get();
    }

}



