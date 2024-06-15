package com.jux.juxbar.ServiceTest;


import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Repository.CocktailRepository;
import com.jux.juxbar.Service.CocktailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CocktailServiceTest {


    @Mock
    private CocktailRepository cocktailRepository;

    @InjectMocks
    private CocktailService cocktailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }


    @Test
    @DisplayName("Service calls Repository and returns List of cocktails")
    public void testGetAllCocktail_CallsRepository(){
        // Given
        List<Cocktail> cocktails = Arrays.asList(new Cocktail(), new Cocktail(), new Cocktail());
        when(cocktailRepository.findAll()).thenReturn(cocktails);

        // When
        Iterable<Cocktail> actualCocktails = cocktailService.getAllCocktails();

        // Then
        assertEquals(cocktails, actualCocktails);
        verify(cocktailRepository).findAll();
    }

    @Test
    @DisplayName("Service calls Repository and returns one cocktail")
    public void testGetCocktail_CallsRepository(){

        //Given
        Cocktail cocktail = new Cocktail();
        cocktail.setId(5);
        cocktail.setIdDrink("500");
        cocktail.setStrDrink("Suze-Cassis");
        cocktail.setStrIngredient1("Suze");
        cocktail.setStrIngredient2("Crème de cassis");
        when(cocktailRepository.findById(5)).thenReturn(Optional.of(cocktail));

        //When
        Optional<Cocktail> actualCocktail = cocktailService.getCocktail(5);

        //Then

        assertThat(actualCocktail).isEqualTo(Optional.of(cocktail));
        verify(cocktailRepository).findById(5);
    }

    @Test
    @DisplayName("Service calls Repository and returns empty Object")
    public void testGetCocktail_CallsRepository_CocktailNotFound(){

        //Given
        when(cocktailRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        //When
        Optional<Cocktail> actualCocktail = cocktailService.getCocktail(2);

        //Then

        assertThat(actualCocktail).isEmpty();
        verify(cocktailRepository).findById(any(Integer.class));
    }

    @Test
    @DisplayName("Service calls Repository and returns cocktail by idDrink")
    public void testGetCocktailByIdDrink_CallsRepository(){

        //Given
        Cocktail cocktail = new Cocktail();
        cocktail.setId(6);
        cocktail.setStrDrink("600");
        cocktail.setStrDrink("Godfather");
        cocktail.setStrIngredient1("Whisky");
        cocktail.setStrIngredient2("Amaretto");
        when(cocktailRepository.findByIdDrink("600")).thenReturn(Optional.of(cocktail));

        //When
        Optional<Cocktail> actualCocktail = cocktailService.getCocktailByIdDrink("600");

        //Then

        assertThat(actualCocktail).isEqualTo(Optional.of(cocktail));
        verify(cocktailRepository).findByIdDrink("600");
    }
}