package com.jux.juxbar.repository;

import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.model.PersonalCocktailImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalCocktailImageRepository extends JpaRepository<PersonalCocktailImage, Integer> {

    Optional<PersonalCocktailImage> findByDrinkName(String drinkName);
}
