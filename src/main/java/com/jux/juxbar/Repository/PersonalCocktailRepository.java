package com.jux.juxbar.Repository;

import com.jux.juxbar.Model.PersonalCocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonalCocktailRepository extends JpaRepository<PersonalCocktail, Integer> {
    @Query("SELECT p FROM PersonalCocktail p WHERE p.ownerName = :ownerName")
    Iterable<PersonalCocktail> findAllByOwnerName(@Param("ownerName") String ownerName);
}
