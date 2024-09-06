package com.jux.juxbar.repository;

import com.jux.juxbar.model.SoftDrink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftDrinkRepository extends JpaRepository<SoftDrink, Integer> {

    Optional<SoftDrink> findByIdDrink(String idDrink);

}

