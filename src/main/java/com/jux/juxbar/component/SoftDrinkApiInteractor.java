package com.jux.juxbar.component;

import com.jux.juxbar.interfaces.DrinkApiInteractorInterface;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.model.SoftDrinkResponse;
import com.jux.juxbar.service.SoftDrinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor

public class SoftDrinkApiInteractor extends Thread implements DrinkApiInteractorInterface {

    private final SoftDrinkService softDrinkService;
    private final RestTemplate restTemplate;

    public void checkUpdateAndDownload() throws InterruptedException {

        ResponseEntity<SoftDrinkResponse> response =
                restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?a=Non_Alcoholic",
                        SoftDrinkResponse.class);
        SoftDrinkResponse softDrinkResponse = response.getBody();
        assert softDrinkResponse != null;
        List<SoftDrink> softDrinks = softDrinkResponse.getDrinks();

        int counter = 0;
        for (SoftDrink softDrink : softDrinks) {
            Optional<SoftDrink> existingSoftDrink = softDrinkService.getDrinkByIdDrink(softDrink.getIdDrink());
            if (existingSoftDrink.isPresent()) {
                log.info("doublon");
            } else {
                ResponseEntity<SoftDrinkResponse> oneResponse = restTemplate.getForEntity(
                        "https://www.thecocktaildb.com/api/json/v2/9973533/lookup.php?i=" + softDrink.getIdDrink(),
                        SoftDrinkResponse.class);
                SoftDrinkResponse oneSoftDrinkResponse = oneResponse.getBody();
                assert oneSoftDrinkResponse != null;
                List<SoftDrink> newSoftDrinks = oneSoftDrinkResponse.getDrinks();
                newSoftDrinks.forEach(softDrinkService::saveDrink);

                counter++;
                sleep(300);

            }
        }
    }

    @Override
    public void downloadImages() {

        Iterable<SoftDrink> softDrinks = softDrinkService.getDrinks();
        softDrinks.forEach(softDrink -> {
            if (softDrinkService.getDrink(softDrink.getId()).get().getImageData() == null) {
                String url = softDrink.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                softDrink.setImageData(imageBytes);
                softDrinkService.saveDrink(softDrink);
                log.info("ONE MORE");
            }

        });
    }

    @Override
    public void downloadPreviews() {

        Iterable<SoftDrink> softDrinks = softDrinkService.getDrinks();
        softDrinks.forEach(softDrink -> {
            if (softDrinkService.getDrink(softDrink.getId()).get().getPreview() == null) {
                String url = softDrink.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                softDrink.setPreview(imageBytes);
                softDrinkService.saveDrink(softDrink);
                log.info("ONE MORE PREVIEW");
            }

        });
    }
}
