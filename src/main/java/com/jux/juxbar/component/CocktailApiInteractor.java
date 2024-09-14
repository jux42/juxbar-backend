package com.jux.juxbar.component;

import com.jux.juxbar.interfaces.DrinkApiInteractorInterface;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.CocktailResponse;
import com.jux.juxbar.service.CocktailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Component
@RequiredArgsConstructor
public class CocktailApiInteractor extends Thread implements DrinkApiInteractorInterface {

    private final CocktailService cocktailService;
    private final RestTemplate restTemplate;

    @Value("apiUrl")
    private String apiUrl;


    @Override
    public void checkUpdateAndDownload() throws InterruptedException {

        ResponseEntity<CocktailResponse> response =
                restTemplate.getForEntity(
                        apiUrl+"filter.php?a=Alcoholic",
                        CocktailResponse.class);
        CocktailResponse cocktailResponse = response.getBody();
        if (cocktailResponse == null) throw new InterruptedException();
        List<Cocktail> cocktails = cocktailResponse.getDrinks();

        int counter = 0;
        for (Cocktail cocktail : cocktails) {
            Optional<Cocktail> existingCocktail = cocktailService.getDrinkByIdDrink(cocktail.getIdDrink());
            if (existingCocktail.isPresent()) {
                log.info("doublon");
            } else {
                ResponseEntity<CocktailResponse> oneResponse = restTemplate.getForEntity(
                        apiUrl+"lookup.php?i=" + cocktail.getIdDrink(),
                        CocktailResponse.class);
                CocktailResponse oneCocktailResponse = oneResponse.getBody();
                assert oneCocktailResponse != null;
                List<Cocktail> newCocktails = oneCocktailResponse.getDrinks();
                newCocktails.forEach(cocktailService::saveDrink);

                counter++;
                sleep(300);


            }
        }
    }

    public void downloadImages() {
        AtomicInteger counter = new AtomicInteger(0);

        Iterable<Cocktail> cocktails = cocktailService.getAllDrinksNoCache();
        cocktails.forEach(cocktail -> {
            if (cocktailService.getDrinkNoCache(cocktail.getId()).get().getImageData() == null) {
                String url = cocktail.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                cocktail.setImageData(imageBytes);
                cocktailService.saveDrink(cocktail);
                counter.getAndIncrement();

            }

        });
        if (counter.get() != 0) {
            counter.get();
        }
    }


    public void downloadPreviews() {

        AtomicInteger counter = new AtomicInteger(0);
        Iterable<Cocktail> cocktails = cocktailService.getAllDrinksNoCache();
        cocktails.forEach(cocktail -> {
            if (cocktailService.getDrinkNoCache(cocktail.getId()).get().getPreview() == null) {
                String url = cocktail.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                cocktail.setPreview(imageBytes);
                cocktailService.saveDrink(cocktail);
                counter.getAndIncrement();
            }

        });

        if (counter.get() != 0) {
            counter.get();
        }
    }
}
