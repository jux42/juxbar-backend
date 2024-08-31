package com.jux.juxbar.Repository;

import com.jux.juxbar.Model.SoftDrink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftDrinkRepository extends JpaRepository<SoftDrink, Integer> {

    Optional<SoftDrink> findByIdDrink(String idDrink);

}

