package com.jux.juxbar.component;

import com.jux.juxbar.interfaces.DrinkApiInteractorInterface;
import com.jux.juxbar.model.Ingredient;
import com.jux.juxbar.model.IngredientImage;
import com.jux.juxbar.model.IngredientResponse;
import com.jux.juxbar.model.SoftDrinkImage;
import com.jux.juxbar.repository.IngredientImageRepository;
import com.jux.juxbar.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;


@Slf4j
@Component
@RequiredArgsConstructor
public class IngredientApiInteractor implements DrinkApiInteractorInterface {

    private final IngredientService ingredientService;
    private  final RestTemplate restTemplate;
    private final IngredientImageRepository ingredientImageRepository;

    @Value("${apiUrl}")
    private String apiUrl;
    @Value("${ingredientImageApiUrl}")
    private String ingredientImageApiUrl;

    @Override
    public void checkUpdateAndDownload() throws InterruptedException {
        for (int i = 1; i < 617; i++) {

            try {
                ResponseEntity<IngredientResponse> response =
                        restTemplate.getForEntity(
                                apiUrl+"lookup.php?iid=" + i,
                                IngredientResponse.class);
                IngredientResponse ingredientResponse = response.getBody();
                assert ingredientResponse != null;
                List<Ingredient> ingredients = ingredientResponse.getIngredients();

                for (Ingredient ingredient : ingredients) {
                    Optional<Ingredient> thatIngredient = ingredientService.getIngredientByIdIngredient(ingredient.getIdIngredient());
                    if (thatIngredient.isPresent()) continue;
                    ingredientService.saveIngredient(ingredient);
                }
            } catch (Exception e) {
                continue;
            }
            sleep(300);
        }
    }

    public void downloadImages() {

        Iterable<Ingredient> ingredients = ingredientService.getIngredients();
        ingredients.forEach(ingredient -> {
            if (ingredientService.getIngredient(ingredient.getId()).get().getImageData() == null) {
                String url = ingredientImageApiUrl + ingredient.getStrIngredient() + ".png";
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                Optional<IngredientImage> ingredientImage = ingredientImageRepository.findByIngredientName(ingredient.getStrIngredient());
                if(ingredientImage.isEmpty()){
                    IngredientImage newIngredientImage = new IngredientImage();
                    newIngredientImage.setIngredientName(ingredient.getStrIngredient());
                    newIngredientImage.setImage(imageBytes);
                    ingredient.setImageData(newIngredientImage);
                    ingredientImageRepository.save(newIngredientImage);
                    ingredientService.saveIngredient(ingredient);
                }else{
                    ingredientImage.get().setImage(imageBytes);
                    ingredient.setImageData(ingredientImage.get());
                    ingredientService.saveIngredient(ingredient);
                }
                log.info("ONE MORE image");
            }
        });
    }

    public void downloadPreviews() {

        Iterable<Ingredient> ingredients = ingredientService.getIngredients();
        ingredients.forEach(ingredient -> {
            if (ingredientService.getIngredient(ingredient.getId()).get().getImageData().getPreview() == null) {
                String url = ingredientImageApiUrl + ingredient.getStrIngredient() + "-Medium.png";
                byte[] imageBytes = restTemplate.getForObject(
                        url, byte[].class);
                Optional<IngredientImage> ingredientImage = ingredientImageRepository.findByIngredientName(ingredient.getStrIngredient());
                if(ingredientImage.isEmpty()){
                    IngredientImage newIngredientImage = new IngredientImage();
                    newIngredientImage.setIngredientName(ingredient.getStrIngredient());
                    newIngredientImage.setPreview(imageBytes);
                    ingredient.setImageData(newIngredientImage);
                    ingredientImageRepository.save(newIngredientImage);
                    ingredientService.saveIngredient(ingredient);
                }else{
                    ingredientImage.get().setPreview(imageBytes);
                    ingredient.setImageData(ingredientImage.get());
                    ingredientService.saveIngredient(ingredient);
                }
                log.info("ONE MORE preview");
            }
        });
    }
}
