package com.jux.juxbar.Repository;

import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {

    Optional<Ingredient> findByIdIngredient(String idDrink);

    Optional<Ingredient> findByStrIngredient(String strIngredient);
}
