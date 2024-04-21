package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.SoftDrink;
import com.jux.juxbar.Model.SoftDrinkResponse;
import com.jux.juxbar.Repository.SoftDrinkRepository;
import com.jux.juxbar.Service.SoftDrinkService;
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
public class SoftDrinkController {

    @Autowired
    SoftDrinkService softDrinkService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    SoftDrinkRepository softDrinkRepository;

    @GetMapping("/softdrinks")
    public Iterable<SoftDrink> getSoftDrinks(){

        return softDrinkService.getSoftDrinks();
    }

    @GetMapping("/softdrink/{id}")
    public Optional<SoftDrink> getSoftDrink(@PathVariable int id){
        return softDrinkService.getSoftDrink(id);    }

    @GetMapping("softdrink/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id){
        return softDrinkService.getSoftDrink(id)
                .map(softDrink -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(softDrink.getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("softdrink/{id}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable int id){
        return softDrinkService.getSoftDrink(id)
                .map(softDrink -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(softDrink.getPreview()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/softdrinks/save")
    public String saveSoftDrinks() throws InterruptedException {
        ResponseEntity<SoftDrinkResponse> response =
                restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?a=Non_Alcoholic",
                        SoftDrinkResponse.class);
        SoftDrinkResponse softDrinkResponse = response.getBody();
        assert softDrinkResponse != null;
        List<SoftDrink> softDrinks = softDrinkResponse.getDrinks();

        return softDrinkService.checkUpdate(softDrinks);

    }


    @GetMapping("softDrinks/saveimages")
    public String saveSoftDrinksImages() {

        Iterable<SoftDrink> softDrinks = softDrinkService.getSoftDrinks();
        softDrinks.forEach(softDrink -> {
            if (softDrinkService.getSoftDrink(softDrink.getId()).get().getImageData() == null) {
                String Url = softDrink.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                softDrink.setImageData(imageBytes);
                softDrinkService.saveSoftDrink(softDrink);
                System.out.println("ONE MORE");
            }



        });
        return "images à jour";
    }
    @GetMapping("softdrinks/savepreviews")
    public String saveCocktailsPreviews() {

        Iterable<SoftDrink> softDrinks = softDrinkService.getSoftDrinks();
        softDrinks.forEach(softDrink -> {
            if (softDrinkService.getSoftDrink(softDrink.getId()).get().getPreview() == null) {
                String Url = softDrink.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                softDrink.setPreview(imageBytes);
                softDrinkService.saveSoftDrink(softDrink);
                System.out.println("ONE MORE PREVIEW");
            }

        });
        return "previews à jour";
    }
}



