package com.jux.juxbar.repository;


import com.jux.juxbar.model.SoftDrinkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftDrinkImageRepository extends JpaRepository<SoftDrinkImage, Integer> {

    Optional<SoftDrinkImage> findByDrinkName(String drinkName);
}
