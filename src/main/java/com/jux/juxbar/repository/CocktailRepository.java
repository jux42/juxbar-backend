package com.jux.juxbar.repository;

import com.jux.juxbar.model.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Integer> {

    Optional<Cocktail> findByIdDrink(String idDrink);

}
