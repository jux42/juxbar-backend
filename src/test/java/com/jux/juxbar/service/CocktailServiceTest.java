package com.jux.juxbar.service;

//package com.jux.juxbar.ServiceTest;
//
//import com.jux.juxbar.Model.Cocktail;
//import com.jux.juxbar.Model.Ingredient;
//import com.jux.juxbar.Repository.CocktailRepository;
//import com.jux.juxbar.Service.CocktailService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@Disabled
//@ExtendWith(MockitoExtension.class)
public class CocktailServiceTest {

}
//
//
//    @Mock
//    private CocktailRepository cocktailRepository;
//
//    @InjectMocks
//    private CocktailService cocktailService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//    }
//
//
//    @Test
//    @DisplayName("Service calls Repository and returns List of cocktails")
//    public void testGetAllCocktail_CallsRepository(){
//        // Given
//        List<Cocktail> cocktails = Arrays.asList(new Cocktail(), new Cocktail(), new Cocktail());
//        when(cocktailRepository.findAll()).thenReturn(cocktails);
//
//        // When
//        Iterable<Cocktail> actualCocktails = cocktailService.getAllCocktails();
//
//        // Then
//        assertEquals(cocktails, actualCocktails);
//        verify(cocktailRepository).findAll();
//    }
//
//    @Test
//    @DisplayName("Service calls Repository and returns one cocktail")
//    public void testGetCocktail_CallsRepository(){
//
//        //Given
//        Cocktail cocktail = new Cocktail();
//        cocktail.setId(5);
//        cocktail.setIdDrink("500");
//        cocktail.setStrDrink("Suze-Cassis");
//        Ingredient ingredient1 = new Ingredient();
//        ingredient1.setId(1);
//        ingredient1.setStrIngredient("Suze");
//        cocktail.setIngredient1(ingredient1);
//
//        Ingredient ingredient2 = new Ingredient();
//        ingredient2.setId(2);
//        ingredient2.setStrIngredient("Crème de cassis");
//        cocktail.setIngredient2(ingredient2);
//        when(cocktailRepository.findById(5)).thenReturn(Optional.of(cocktail));
//
//        //When
//        Optional<Cocktail> actualCocktail = cocktailService.getCocktail(5);
//
//        //Then
//
//        assertThat(actualCocktail).isEqualTo(Optional.of(cocktail));
//        verify(cocktailRepository).findById(5);
//    }
//
//    @Test
//    @DisplayName("Service calls Repository and returns empty Object")
//    public void testGetCocktail_CallsRepository_CocktailNotFound(){
//
//        //Given
//        when(cocktailRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
//
//        //When
//        Optional<Cocktail> actualCocktail = cocktailService.getCocktail(2);
//
//        //Then
//
//        assertThat(actualCocktail).isEmpty();
//        verify(cocktailRepository).findById(any(Integer.class));
//    }
//
//    @Test
//    @DisplayName("Service calls Repository and returns cocktail by idDrink")
//    public void testGetCocktailByIdDrink_CallsRepository(){
//
//        //Given
//        Cocktail cocktail = new Cocktail();
//        cocktail.setId(6);
//        cocktail.setStrDrink("600");
//        cocktail.setStrDrink("Godfather");
//        Ingredient ingredient1 = new Ingredient();
//        ingredient1.setId(1);
//        ingredient1.setStrIngredient("Whisky");
//        cocktail.setIngredient1(ingredient1);
//
//        Ingredient ingredient2 = new Ingredient();
//        ingredient2.setId(2);
//        ingredient2.setStrIngredient("Amaretto");
//        cocktail.setIngredient2(ingredient2);
//        when(cocktailRepository.findByIdDrink("600")).thenReturn(Optional.of(cocktail));
//
//        //When
//        Optional<Cocktail> actualCocktail = cocktailService.getCocktailByIdDrink("600");
//
//        //Then
//
//        assertThat(actualCocktail).isEqualTo(Optional.of(cocktail));
//        verify(cocktailRepository).findByIdDrink("600");
//    }
//
//
//}
