package com.jux.juxbar.repository;

import com.jux.juxbar.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findByIdIngredient(String idDrink);

    Optional<Ingredient> findByStrIngredient(String strIngredient);
}
