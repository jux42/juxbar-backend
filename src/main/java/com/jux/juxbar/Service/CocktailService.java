package com.jux.juxbar.Service;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.CocktailResponse;
import com.jux.juxbar.Repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CocktailService extends Thread{
    @Autowired
    CocktailRepository cocktailRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ImageCompressor imageCompressor;


    public String checkUpdate() throws InterruptedException {

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
            if (existingCocktail.isPresent())
            {
                System.out.println("doublon");
            }else{
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
        return counter == 0 ? "pas de mise à jour"
                : "mise à jour des cocktails effectuée, "+counter+" cocktails ajoutés" ;
    }

    public Optional<Cocktail> getCocktailByIdDrink(String idDrink){
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

    public ResponseEntity<?> getImage(int id) {
        return this.getCocktail(id)
                .map(cocktail -> {
                    try {
                        byte[] compressed = imageCompressor.compress(cocktail.getImageData(), "jpg");
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
        return this.getCocktail(id)
                .map(cocktail -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(cocktail.getPreview()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    public String saveCocktailsImages() {
        AtomicInteger counter = new AtomicInteger(0);

        Iterable<Cocktail> cocktails = this.getAllCocktails();
        cocktails.forEach(cocktail -> {
            if (this.getCocktail(cocktail.getId()).get().getImageData() == null) {
                String Url = cocktail.getStrDrinkThumb();
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                cocktail.setImageData(imageBytes);
                this.saveCocktail(cocktail);
                counter.getAndIncrement();

            }

        });
        return counter.get() == 0 ? "pas de nouvelles images"
                :  "Nombre d'images ajoutées : " + counter.get();
    }


    public String saveCocktailsPreviews() {

        AtomicInteger counter = new AtomicInteger(0);
        Iterable<Cocktail> cocktails = this.getAllCocktails();
        cocktails.forEach(cocktail -> {
            if (this.getCocktail(cocktail.getId()).get().getPreview() == null) {
                String Url = cocktail.getStrDrinkThumb() + "/preview";
                byte[] imageBytes = restTemplate.getForObject(
                        Url, byte[].class);
                cocktail.setPreview(imageBytes);
                this.saveCocktail(cocktail);
                counter.getAndIncrement();
            }

        });

        return counter.get() == 0 ? "pas de nouvelle preview"
                :  "Nombre de previews ajoutées : " + counter.get();
    }

}
