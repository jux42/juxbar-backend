package com.jux.juxbar.Service;

import com.jux.juxbar.Model.SoftDrink;
import com.jux.juxbar.Model.SoftDrinkResponse;
import com.jux.juxbar.Repository.SoftDrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class SoftDrinkService extends Thread{
    @Autowired
    SoftDrinkRepository softDrinkRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ImageCompressor imageCompressor;

    public String checkUpdate() throws InterruptedException {

        ResponseEntity<SoftDrinkResponse> response =
                restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?a=Non_Alcoholic",
                        SoftDrinkResponse.class);
        SoftDrinkResponse softDrinkResponse = response.getBody();
        assert softDrinkResponse != null;
        List<SoftDrink> softDrinks = softDrinkResponse.getDrinks();

        int counter = 0;
        for (SoftDrink softDrink : softDrinks) {
            Optional<SoftDrink> existingSoftDrink = this.getSoftDrinkByIdDrink(softDrink.getIdDrink());
            if (existingSoftDrink.isPresent())
            {
                System.out.println("doublon");
            }else{
                ResponseEntity<SoftDrinkResponse> oneResponse = restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/lookup.php?i=" + softDrink.getIdDrink(),
                        SoftDrinkResponse.class);
                SoftDrinkResponse oneSoftDrinkResponse = oneResponse.getBody();
                assert oneSoftDrinkResponse != null;
                List<SoftDrink> newSoftDrinks = oneSoftDrinkResponse.getDrinks();
                newSoftDrinks.forEach(this::saveSoftDrink);

                counter++;
                sleep(300);

            }
        }
        return counter == 0 ? "pas de mise à jour"
                : "mise à jour des softDrinks effectuée";
    }

    public Optional<SoftDrink> getSoftDrinkByIdDrink(String idDrink){
        return softDrinkRepository.findByIdDrink(idDrink);
    }
    public void saveSoftDrink(SoftDrink softDrink) {
        softDrinkRepository.save(softDrink);

    }

    public Optional<SoftDrink> getSoftDrink(int id) {
        return softDrinkRepository.findById(id);
    }

    public Iterable<SoftDrink> getSoftDrinks() {
        return softDrinkRepository.findAll();
    }

    public ResponseEntity<?> getImage(int id) {
        return this.getSoftDrink(id)
                .map(softDrink -> {
                    try {
                        byte[] compressed = imageCompressor.compress(softDrink.getImageData(), "jpg");
                        return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(compressed);
                    } catch (Exception e) {
                        return ResponseEntity.internalServerError().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<byte[]> getPreview(int id) {
        return this.getSoftDrink(id)
                .map(softDrink -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(softDrink.getPreview()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public String saveSoftDrinksImages() {

        Iterable<SoftDrink> softDrinks = this.getSoftDrinks();
        softDrinks.forEach(softDrink -> {
            if (this.getSoftDrink(softDrink.getId()).get().getImageData() == null) {
                String Url = softDrink.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                softDrink.setImageData(imageBytes);
                this.saveSoftDrink(softDrink);
                System.out.println("ONE MORE");
            }

        });
        return "images à jour";
    }

    public String saveCocktailsPreviews() {

        Iterable<SoftDrink> softDrinks = this.getSoftDrinks();
        softDrinks.forEach(softDrink -> {
            if (this.getSoftDrink(softDrink.getId()).get().getPreview() == null) {
                String Url = softDrink.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                softDrink.setPreview(imageBytes);
                this.saveSoftDrink(softDrink);
                System.out.println("ONE MORE PREVIEW");
            }

        });
        return "previews à jour";
    }
}
