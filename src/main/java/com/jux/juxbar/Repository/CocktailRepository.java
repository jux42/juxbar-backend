package com.jux.juxbar.Repository;

import com.jux.juxbar.Model.Cocktail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CocktailRepository extends CrudRepository<Cocktail, Integer> {

    Optional<Cocktail> findByIdDrink(String idDrink);

}
