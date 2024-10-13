package com.jux.juxbar.service;

import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.model.SoftDrink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FavouritesServiceTest {

    @Mock
    private JuxBarUserService juxBarUserService;

    @Mock
    private CocktailService cocktailService;

    @Mock
    private SoftDrinkService softDrinkService;

    @Mock
    private Principal principal;

    @InjectMocks
    private FavouritesService favouritesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return favorite cocktails for a user")
    void getFavouriteCocktails_ShouldReturnFavouriteCocktails() {
        // Given
        String username = "testUser";
        JuxBarUser user = new JuxBarUser();
        user.setFavourite_cocktails(new ArrayList<>());
        user.getFavourite_cocktails().add(new Cocktail());

        when(principal.getName()).thenReturn(username);
        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);

        // When
        Iterable<Cocktail> result = favouritesService.getFavouriteCocktails(principal);

        // Then
        assertThat(result).isNotEmpty();
        verify(juxBarUserService).getJuxBarUserByUsername(username);
    }

    @Test
    @DisplayName("Should add a cocktail to favorites")
    void addFavoriteCocktail_ShouldAddCocktailToFavorites() {
        // Given
        String username = "testUser";
        Integer cocktailId = 1;
        JuxBarUser user = new JuxBarUser();
        user.setFavourite_cocktails(new ArrayList<>());
        Cocktail cocktail = new Cocktail();

        when(principal.getName()).thenReturn(username);
        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);
        when(cocktailService.getDrink(cocktailId)).thenReturn(Optional.of(cocktail));

        // When
        ResponseEntity<String> result = favouritesService.addFavoriteCocktail(cocktailId, principal);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(juxBarUserService).saveJuxBarUser(user);
        assertThat(user.getFavourite_cocktails()).contains(cocktail);
    }

    @Test
    @DisplayName("Should remove a cocktail from favorites")
    void removeFavoriteCocktail_ShouldRemoveCocktailFromFavorites() {
        // Given
        String username = "testUser";
        Integer cocktailId = 1;
        JuxBarUser user = new JuxBarUser();
        user.setFavourite_cocktails(new ArrayList<>());
        Cocktail cocktail = new Cocktail();
        user.getFavourite_cocktails().add(cocktail);

        when(principal.getName()).thenReturn(username);
        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);
        when(cocktailService.getDrink(cocktailId)).thenReturn(Optional.of(cocktail));

        // When
        ResponseEntity<String> result = favouritesService.removeFavoriteCocktail(cocktailId, principal);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(juxBarUserService).saveJuxBarUser(user);
        assertThat(user.getFavourite_cocktails()).doesNotContain(cocktail);
    }

    @Test
    @DisplayName("Should return favorite soft drinks for a user")
    void getFavouriteSoftDrinks_ShouldReturnFavouriteSoftDrinks() {
        // Given
        String username = "testUser";
        JuxBarUser user = new JuxBarUser();
        user.setFavourite_softdrinks(new ArrayList<>());
        user.getFavourite_softdrinks().add(new SoftDrink());

        when(principal.getName()).thenReturn(username);
        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);

        // When
        Iterable<SoftDrink> result = favouritesService.getFavouriteSoftDrinks(principal);

        // Then
        assertThat(result).isNotEmpty();
        verify(juxBarUserService).getJuxBarUserByUsername(username);
    }

    @Test
    @DisplayName("Should add a soft drink to favorites")
    void addFavoriteSoftDrink_ShouldAddSoftDrinkToFavorites() {
        // Given
        String username = "testUser";
        Integer softDrinkId = 1;
        JuxBarUser user = new JuxBarUser();
        user.setFavourite_softdrinks(new ArrayList<>());
        SoftDrink softDrink = new SoftDrink();

        when(principal.getName()).thenReturn(username);
        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);
        when(softDrinkService.getDrink(softDrinkId)).thenReturn(Optional.of(softDrink));

        // When
        ResponseEntity<String> result = favouritesService.addFavoriteSoftDrink(softDrinkId, principal);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(juxBarUserService).saveJuxBarUser(user);
        assertThat(user.getFavourite_softdrinks()).contains(softDrink);
    }

    @Test
    @DisplayName("Should remove a soft drink from favorites")
    void removeFavoriteSoftDrink_ShouldRemoveSoftDrinkFromFavorites() {
        // Given
        String username = "testUser";
        Integer softDrinkId = 1;
        JuxBarUser user = new JuxBarUser();
        user.setFavourite_softdrinks(new ArrayList<>());
        SoftDrink softDrink = new SoftDrink();
        user.getFavourite_softdrinks().add(softDrink);

        when(principal.getName()).thenReturn(username);
        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);
        when(softDrinkService.getDrink(softDrinkId)).thenReturn(Optional.of(softDrink));

        // When
        ResponseEntity<String> result = favouritesService.removeFavoriteSoftDrink(softDrinkId, principal);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(juxBarUserService).saveJuxBarUser(user);
        assertThat(user.getFavourite_softdrinks()).doesNotContain(softDrink);
    }
}
