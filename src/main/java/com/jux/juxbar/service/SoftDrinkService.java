package com.jux.juxbar.service;

import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.model.SoftDrinkResponse;
import com.jux.juxbar.repository.SoftDrinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class SoftDrinkService extends Thread {

    private final SoftDrinkRepository softDrinkRepository;
    private final ImageCompressor imageCompressor;
    private final RestTemplate restTemplate;

    public void checkUpdate() throws InterruptedException {

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
            if (existingSoftDrink.isPresent()) {
                log.info("doublon");
            } else {
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
    }

    public Optional<SoftDrink> getSoftDrinkByIdDrink(String idDrink) {
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

    public ResponseEntity<byte[]> getImage(int id) {
        return this.getSoftDrink(id)
                .map(softDrink -> {

                    byte[] compressed = null;
                    try {
                        compressed = imageCompressor.compress(softDrink.getImageData(), "jpg");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(compressed);

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

    public void saveSoftDrinksImages() {

        Iterable<SoftDrink> softDrinks = this.getSoftDrinks();
        softDrinks.forEach(softDrink -> {
            if (this.getSoftDrink(softDrink.getId()).get().getImageData() == null) {
                String url = softDrink.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                softDrink.setImageData(imageBytes);
                this.saveSoftDrink(softDrink);
                log.info("ONE MORE");
            }

        });
    }

    public void saveCocktailsPreviews() {

        Iterable<SoftDrink> softDrinks = this.getSoftDrinks();
        softDrinks.forEach(softDrink -> {
            if (this.getSoftDrink(softDrink.getId()).get().getPreview() == null) {
                String url = softDrink.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                softDrink.setPreview(imageBytes);
                this.saveSoftDrink(softDrink);
                log.info("ONE MORE PREVIEW");
            }

        });
    }
}