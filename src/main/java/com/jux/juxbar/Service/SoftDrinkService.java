package com.jux.juxbar.Service;

import com.jux.juxbar.Model.SoftDrink;
import com.jux.juxbar.Model.SoftDrinkResponse;
import com.jux.juxbar.Repository.SoftDrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String checkUpdate(List<SoftDrink> softDrinks) throws InterruptedException {

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



}