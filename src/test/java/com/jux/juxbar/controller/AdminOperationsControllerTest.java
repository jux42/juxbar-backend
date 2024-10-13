package com.jux.juxbar.controller;

import com.jux.juxbar.service.AdminOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AdminOperationsControllerTest {

    @Mock
    private AdminOperationsService adminOperationsService;

    @InjectMocks
    private AdminOperationsController adminOperationsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return trashed cocktails list of a user")
    void listTrashedCocktailsOfUser_ShouldReturnTrashedCocktails() {
        // Given
        String username = "testUser";
        List<Integer> trashedCocktails = List.of(1, 2, 3);
        when(adminOperationsService.listTrashedCocktailsOfUser(username)).thenReturn(trashedCocktails);

        // When
        ResponseEntity<List<Integer>> response = adminOperationsController.listTrashedCocktailsOfUser(username);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(trashedCocktails);
    }

    @Test
    @DisplayName("Should restore one trashed cocktail for a user")
    void restoreOneTrashedCocktail_ShouldRestoreCocktail() {
        // Given
        String username = "testUser";
        Integer cocktailId = 1;
        String expectedResponse = "Cocktail restored";
        when(adminOperationsService.restoreOneTrashedCocktail(username, cocktailId)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = adminOperationsController.restoreOneTrashedCocktail(cocktailId, username);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should trash one personal cocktail for a user")
    void trashOnePersonalCocktail_ShouldTrashCocktail() {
        // Given
        String username = "testUser";
        Integer cocktailId = 1;
        String expectedResponse = "Cocktail trashed";
        when(adminOperationsService.trashOnePersonalCocktail(username, cocktailId)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = adminOperationsController.trashOnPersonalCocktail(cocktailId, username);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should restore all trashed cocktails of a user")
    void restoreAllCocktailsOfUser_ShouldRestoreAllCocktails() {
        // Given
        String username = "testUser";
        String expectedResponse = "All cocktails restored";
        when(adminOperationsService.restoreAllCocktailsOfUser(username)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = adminOperationsController.restoreAllCocktailsOfUser(username);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }
}
