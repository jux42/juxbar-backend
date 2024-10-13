package com.jux.juxbar.controller;

import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.service.FavouritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class FavouritesControllerTest {

    @Mock
    private FavouritesService favouritesService;

    @Mock
    private Principal principal;

    @InjectMocks
    private FavouritesController favouritesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return favourite cocktails of the user")
    void getFavouriteCocktails_ShouldReturnFavouriteCocktails() {
        // Given
        List<Cocktail> favouriteCocktails = List.of(new Cocktail(), new Cocktail());
        when(favouritesService.getFavouriteCocktails(principal)).thenReturn(favouriteCocktails);

        // When
        ResponseEntity<Iterable<Cocktail>> response = favouritesController.getFavouriteCocktails(principal);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(favouriteCocktails);
    }

    @Test
    @DisplayName("Should add a cocktail to the user's favourites")
    void addFavoriteCocktail_ShouldAddFavouriteCocktail() {
        // Given
        Integer cocktailId = 1;
        String expectedResponse = "Cocktail added to favourites";
        when(favouritesService.addFavoriteCocktail(cocktailId, principal)).thenReturn(ResponseEntity.ok(expectedResponse));

        // When
        ResponseEntity<String> response = favouritesController.addFavoriteCocktail(cocktailId, principal);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should remove a cocktail from the user's favourites")
    void removeFavoriteCocktail_ShouldRemoveFavouriteCocktail() {
        // Given
        Integer cocktailId = 1;
        String expectedResponse = "Cocktail removed from favourites";
        when(favouritesService.removeFavoriteCocktail(cocktailId, principal)).thenReturn(ResponseEntity.ok(expectedResponse));

        // When
        ResponseEntity<String> response = favouritesController.removeFavoriteCocktail(cocktailId, principal);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should return favourite soft drinks of the user")
    void getFavouriteSoftDrinks_ShouldReturnFavouriteSoftDrinks() {
        // Given
        List<SoftDrink> favouriteSoftDrinks = List.of(new SoftDrink(), new SoftDrink());
        when(favouritesService.getFavouriteSoftDrinks(principal)).thenReturn(favouriteSoftDrinks);

        // When
        Iterable<SoftDrink> result = favouritesController.getFavouriteSoftDrinks(principal);

        // Then
        assertThat(result).isEqualTo(favouriteSoftDrinks);
    }

    @Test
    @DisplayName("Should add a soft drink to the user's favourites")
    void addFavoriteSoftDrink_ShouldAddFavouriteSoftDrink() {
        // Given
        Integer softDrinkId = 1;
        String expectedResponse = "Soft drink added to favourites";
        when(favouritesService.addFavoriteSoftDrink(softDrinkId, principal)).thenReturn(ResponseEntity.ok(expectedResponse));

        // When
        ResponseEntity<String> response = favouritesController.addFavoriteSoftDrink(softDrinkId, principal);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should remove a soft drink from the user's favourites")
    void removeFavoriteSoftDrink_ShouldRemoveFavouriteSoftDrink() {
        // Given
        Integer softDrinkId = 1;
        String expectedResponse = "Soft drink removed from favourites";
        when(favouritesService.removeFavoriteSoftDrink(softDrinkId, principal)).thenReturn(ResponseEntity.ok(expectedResponse));

        // When
        ResponseEntity<String> response = favouritesController.removeFavoriteSoftDrink(softDrinkId, principal);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }
}
