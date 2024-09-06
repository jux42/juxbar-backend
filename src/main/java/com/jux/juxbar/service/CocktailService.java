package com.jux.juxbar.service;

import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.CocktailResponse;
import com.jux.juxbar.repository.CocktailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class CocktailService extends Thread {

    private final CocktailRepository cocktailRepository;
    private final ImageCompressor imageCompressor;
    private final RestTemplate restTemplate;

    public void checkUpdate() throws InterruptedException {

        ResponseEntity<CocktailResponse> response =
                restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?a=Alcoholic",
                        CocktailResponse.class);
        CocktailResponse cocktailResponse = response.getBody();
        if (cocktailResponse == null) throw new InterruptedException();
        List<Cocktail> cocktails = cocktailResponse.getDrinks();

        int counter = 0;
        for (Cocktail cocktail : cocktails) {
            Optional<Cocktail> existingCocktail = this.getCocktailByIdDrink(cocktail.getIdDrink());
            if (existingCocktail.isPresent()) {
                log.info("doublon");
            } else {
                ResponseEntity<CocktailResponse> oneResponse = restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/lookup.php?i=" + cocktail.getIdDrink(),
                        CocktailResponse.class);
                CocktailResponse oneCocktailResponse = oneResponse.getBody();
                assert oneCocktailResponse != null;
                List<Cocktail> newCocktails = oneCocktailResponse.getDrinks();
                newCocktails.forEach(this::saveCocktail);

                counter++;
                sleep(300);

            }
        }
    }

    public Optional<Cocktail> getCocktailByIdDrink(String idDrink) {
        return cocktailRepository.findByIdDrink(idDrink);
    }

    public void saveCocktail(Cocktail cocktail) {
        cocktailRepository.save(cocktail);

    }

    public Optional<Cocktail> getCocktail(int id) {
        return cocktailRepository.findById(id);
    }

    public Iterable<Cocktail> getAllCocktails() {
        return cocktailRepository.findAll();
    }

    public Page<Cocktail> getCocktails(Pageable pageable) {
        return cocktailRepository.findAll(pageable);
    }

    public ResponseEntity<byte[]> getImage(int id) {
        return this.getCocktail(id)
                .map(cocktail -> {
                    byte[] compressedImage;
                    try {
                        compressedImage = imageCompressor.compress(cocktail.getImageData(), "jpg");
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(compressedImage);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<byte[]> getPreview(int id) {
        return this.getCocktail(id)
                .map(cocktail -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(cocktail.getPreview()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    public void saveCocktailsImages() {
        AtomicInteger counter = new AtomicInteger(0);

        Iterable<Cocktail> cocktails = this.getAllCocktails();
        cocktails.forEach(cocktail -> {
            if (this.getCocktail(cocktail.getId()).get().getImageData() == null) {
                String url = cocktail.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                cocktail.setImageData(imageBytes);
                this.saveCocktail(cocktail);
                counter.getAndIncrement();

            }

        });
        if (counter.get() != 0) {
            counter.get();
        }
    }


    public void saveCocktailsPreviews() {

        AtomicInteger counter = new AtomicInteger(0);
        Iterable<Cocktail> cocktails = this.getAllCocktails();
        cocktails.forEach(cocktail -> {
            if (this.getCocktail(cocktail.getId()).get().getPreview() == null) {
                String url = cocktail.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                cocktail.setPreview(imageBytes);
                this.saveCocktail(cocktail);
                counter.getAndIncrement();
            }

        });

        if (counter.get() != 0) {
            counter.get();
        }
    }

}