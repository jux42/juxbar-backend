package com.jux.juxbar.repository;

import com.jux.juxbar.model.PersonalCocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonalCocktailRepository extends JpaRepository<PersonalCocktail, Integer> {


    List<PersonalCocktail> findByOwnerName(String ownername);
}

