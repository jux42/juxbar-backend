package com.jux.juxbar.Repository;

import com.jux.juxbar.Model.PersonalCocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonalCocktailRepository extends JpaRepository<PersonalCocktail, Integer> {

    Iterable<PersonalCocktail> findAllByOwner_Username(String ownerName);

    List<PersonalCocktail> findByOwner_Username(String ownername);
}
