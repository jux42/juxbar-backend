package com.jux.juxbar.repository;

import com.jux.juxbar.model.CocktailImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CocktailImageRepository extends JpaRepository<CocktailImage, Integer> {

    Optional<CocktailImage> findByDrinkName(String name);
}
