package com.jux.juxbar.repository;


import com.jux.juxbar.model.IngredientImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientImageRepository extends JpaRepository<IngredientImage, Integer> {

    Optional<IngredientImage> findByIngredientName (String name);
}
