package com.jux.juxbar.interfaces;

import com.jux.juxbar.model.Drink;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface DrinkServiceInterface<T extends Drink> {


    Optional<T> getDrinkByIdDrink(String idDrink);

    void saveDrink(T drink);

    Optional<T> getDrink(int id);

    Optional<T> getDrinkNoCache(int id);

    Iterable<T> getAllDrinks();

    Iterable<T> getAllDrinksNoCache();

    Iterable<T> getDrinks(Pageable pageable);

    ResponseEntity<byte[]> getImage(int id);

    ResponseEntity<byte[]> getImageNoCache(int id);

    ResponseEntity<byte[]> getPreview(int id);

}


