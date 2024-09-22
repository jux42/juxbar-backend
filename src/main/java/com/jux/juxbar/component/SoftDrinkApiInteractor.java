package com.jux.juxbar.component;

import com.jux.juxbar.interfaces.DrinkApiInteractorInterface;
import com.jux.juxbar.model.CocktailImage;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.model.SoftDrinkImage;
import com.jux.juxbar.model.SoftDrinkResponse;
import com.jux.juxbar.repository.SoftDrinkImageRepository;
import com.jux.juxbar.service.SoftDrinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final SoftDrinkImageRepository softDrinkImageRepository;

    @Value("${apiUrl}")
    private String apiUrl;

    public void checkUpdateAndDownload() throws InterruptedException {

        ResponseEntity<SoftDrinkResponse> response =
                restTemplate.getForEntity(
                        apiUrl+"filter.php?a=Non_Alcoholic",
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
                        apiUrl+"lookup.php?i=" + softDrink.getIdDrink(),
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
                Optional<SoftDrinkImage> softDrinkImage = softDrinkImageRepository.findByDrinkName(softDrink.getStrDrink());
                if(softDrinkImage.isEmpty()){
                    SoftDrinkImage newSoftDrinkImage = new SoftDrinkImage();
                    newSoftDrinkImage.setDrinkName(softDrink.getStrDrink());
                    newSoftDrinkImage.setImage(imageBytes);
                    softDrink.setImageData(newSoftDrinkImage);
                    softDrinkImageRepository.save(newSoftDrinkImage);
                    softDrinkService.saveDrink(softDrink);
                }else{
                    softDrinkImage.get().setImage(imageBytes);
                    softDrink.setImageData(softDrinkImage.get());
                    softDrinkService.saveDrink(softDrink);
                }
                log.info("ONE MORE");
            }

        });
    }

    @Override
    public void downloadPreviews() {

        Iterable<SoftDrink> softDrinks = softDrinkService.getDrinks();
        softDrinks.forEach(softDrink -> {
            if (softDrinkService.getDrink(softDrink.getId()).get().getImageData().getPreview() == null) {
                String url = softDrink.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                Optional<SoftDrinkImage> softDrinkImage = softDrinkImageRepository.findByDrinkName(softDrink.getStrDrink());
                if(softDrinkImage.isEmpty()){
                    SoftDrinkImage newSoftDrinkImage = new SoftDrinkImage();
                    newSoftDrinkImage.setDrinkName(softDrink.getStrDrink());
                    newSoftDrinkImage.setPreview(imageBytes);
                    softDrink.setImageData(newSoftDrinkImage);
                    softDrinkImageRepository.save(newSoftDrinkImage);
                    softDrinkService.saveDrink(softDrink);
                }else{
                    softDrinkImage.get().setPreview(imageBytes);
                    softDrink.setImageData(softDrinkImage.get());
                    softDrinkService.saveDrink(softDrink);
                }
                log.info("ONE MORE PREVIEW");
            }

        });
    }
}
