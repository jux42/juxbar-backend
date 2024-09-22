package com.jux.juxbar.component;

import com.jux.juxbar.interfaces.DrinkApiInteractorInterface;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.CocktailImage;
import com.jux.juxbar.model.CocktailResponse;
import com.jux.juxbar.repository.CocktailImageRepository;
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
    private final CocktailImageRepository cocktailImageRepository;

    @Value("${apiUrl}")
    private String apiUrl;


    @Override
    public void checkUpdateAndDownload() throws InterruptedException {

        String url = apiUrl+"filter.php?a=Alcoholic";

        log.info("Checking for update on {}", url);
        ResponseEntity<CocktailResponse> response =
                restTemplate.getForEntity(
                        url,
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
        this.downloadImages();
    }

    public void downloadImages() {
        AtomicInteger counter = new AtomicInteger(0);

        Iterable<Cocktail> cocktails = cocktailService.getAllDrinksNoCache();
        cocktails.forEach(cocktail -> {
            if (cocktailService.getDrinkNoCache(cocktail.getId()).get().getImageData() == null) {
                String url = cocktail.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                Optional<CocktailImage> cocktailImage = cocktailImageRepository.findByDrinkName(cocktail.getStrDrink());
                if(cocktailImage.isEmpty()){
                    CocktailImage newCocktailImage = new CocktailImage();
                    newCocktailImage.setDrinkName(cocktail.getStrDrink());
                    newCocktailImage.setImage(imageBytes);
                    cocktail.setImageData(newCocktailImage);
                    cocktailImageRepository.save(newCocktailImage);
                    cocktailService.saveDrink(cocktail);
                }else{
                    cocktailImage.get().setImage(imageBytes);
                    cocktail.setImageData(cocktailImage.get());
                    cocktailService.saveDrink(cocktail);
                }

                counter.getAndIncrement();

            }

        });
        if (counter.get() != 0) {
            counter.get();
        }
        this.downloadPreviews();
    }


    public void downloadPreviews() {

        AtomicInteger counter = new AtomicInteger(0);
        Iterable<Cocktail> cocktails = cocktailService.getAllDrinksNoCache();
        cocktails.forEach(cocktail -> {
            if (cocktailService.getDrinkNoCache(cocktail.getId()).get().getImageData().getPreview() == null) {
                String url = cocktail.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                Optional<CocktailImage> cocktailImage = cocktailImageRepository.findByDrinkName(cocktail.getStrDrink());
                if (cocktailImage.isEmpty()) {
                    CocktailImage newCocktailImage = new CocktailImage();
                    newCocktailImage.setPreview(imageBytes);
                    newCocktailImage.setDrinkName(cocktail.getStrDrink());
                    cocktail.setImageData(newCocktailImage);
                    cocktailImageRepository.save(newCocktailImage);
                    cocktailService.saveDrink(cocktail);
                } else {
                    cocktailImage.get().setPreview(imageBytes);
                    cocktail.setImageData(cocktailImage.get());
                    cocktailService.saveDrink(cocktail);
                }

            }
        });

        if (counter.get() != 0) {
            counter.get();
        }
    }
}
