package com.jux.juxbar.repository;

import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonalCocktailRepository extends JpaRepository<PersonalCocktail, Integer> {


    List<PersonalCocktail> findByOwnerName(String ownername);


    List<PersonalCocktail> findByOwnerNameAndState(String username, State state);
}

