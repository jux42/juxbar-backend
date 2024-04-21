package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.CocktailResponse;
import com.jux.juxbar.Repository.CocktailRepository;
import com.jux.juxbar.Service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class CocktailController {

    @Autowired
    CocktailService cocktailService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    CocktailRepository cocktailRepository;

    @GetMapping("/cocktails")
    public Iterable<Cocktail> getCocktails(){

        return cocktailService.getCocktails();
    }

    @GetMapping("/cocktail/{id}")
    public Optional<Cocktail> getCocktail(@PathVariable int id){
        return cocktailService.getCocktail(id);    }

    @GetMapping("/cocktail/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id){
        return cocktailService.getCocktail(id)
                .map(cocktail -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(cocktail.getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/cocktail/{id}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable int id){
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
        assert cocktailResponse != null;
        List<Cocktail> cocktails = cocktailResponse.getDrinks();

        return cocktailService.checkUpdate(cocktails);

    }


    @GetMapping("cocktails/saveimages")
    public String saveCocktailsImages() {

        Iterable<Cocktail> cocktails = cocktailService.getCocktails();
        cocktails.forEach(cocktail -> {
            if (cocktailService.getCocktail(cocktail.getId()).get().getImageData() == null) {
            String Url = cocktail.getStrDrinkThumb();
            byte[] imageBytes = restTemplate.getForObject(
                    Url, byte[].class);
            cocktail.setImageData(imageBytes);
            cocktailService.saveCocktail(cocktail);
            System.out.println("ONE MORE");
            }

        });
        return "images à jour";
    }

    @GetMapping("cocktails/savepreviews")
    public String saveCocktailsPreviews() {

        Iterable<Cocktail> cocktails = cocktailService.getCocktails();
        cocktails.forEach(cocktail -> {
            if (cocktailService.getCocktail(cocktail.getId()).get().getPreview() == null) {
                String Url = cocktail.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                cocktail.setPreview(imageBytes);
                cocktailService.saveCocktail(cocktail);
                System.out.println("ONE MORE PREVIEW");
            }

        });
        return "previews à jour";
    }

}



