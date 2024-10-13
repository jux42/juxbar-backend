package com.jux.juxbar.controller;

import com.jux.juxbar.component.IngredientApiInteractor;
import com.jux.juxbar.model.Ingredient;
import com.jux.juxbar.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private IngredientApiInteractor ingredientApiInteractor;

    @InjectMocks
    private IngredientController ingredientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should download ingredients and return status")
    void downloadIngredients_ShouldReturnStatusOk() throws InterruptedException {
        // Given
        doNothing().when(ingredientApiInteractor).checkUpdateAndDownload();

        // When
        ResponseEntity<String> response = ingredientController.downloadIngredients();

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo("Ingredients à jour");
        verify(ingredientApiInteractor).checkUpdateAndDownload();
    }

    @Test
    @DisplayName("Should return ingredient by name")
    void getIngredientByName_ShouldReturnIngredient() {
        // Given
        String ingredientName = "Vodka";
        Optional<Ingredient> ingredient = Optional.of(new Ingredient());
        when(ingredientService.getIngredientByName(ingredientName)).thenReturn(ingredient);

        // When
        Optional<Ingredient> result = ingredientController.getIngredientByName(ingredientName);

        // Then
        assertThat(result).isEqualTo(ingredient);
        verify(ingredientService).getIngredientByName(ingredientName);
    }

    @Test
    @DisplayName("Should return ingredient by id")
    void getIngredientById_ShouldReturnIngredient() {
        // Given
        int ingredientId = 1;
        Optional<Ingredient> ingredient = Optional.of(new Ingredient());
        when(ingredientService.getIngredient(ingredientId)).thenReturn(ingredient);

        // When
        Optional<Ingredient> result = ingredientController.getIngredientByName(ingredientId);

        // Then
        assertThat(result).isEqualTo(ingredient);
        verify(ingredientService).getIngredient(ingredientId);
    }

    @Test
    @DisplayName("Should return all ingredients asynchronously")
    void getIngredients_ShouldReturnAllIngredientsAsync() throws Exception {
        // Given
        CompletableFuture<Iterable<Ingredient>> futureIngredients = CompletableFuture.completedFuture(List.of(new Ingredient()));
        when(ingredientService.getAllIngredients()).thenReturn(List.of(new Ingredient()));

        // When
        CompletableFuture<Iterable<Ingredient>> result = ingredientController.getIngredients();

        // Then
        assertThat(result.get()).isEqualTo(futureIngredients.get());
        verify(ingredientService).getAllIngredients();
    }

    @Test
    @DisplayName("Should return ingredient image")
    void getImage_ShouldReturnImageBytes() {
        // Given
        String ingredientName = "Vodka";
        ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok(new byte[]{});
        when(ingredientService.getImage(ingredientName)).thenReturn(expectedResponse);

        // When
        ResponseEntity<byte[]> response = ingredientController.getImage(ingredientName);

        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(ingredientService).getImage(ingredientName);
    }

    @Test
    @DisplayName("Should return ingredient preview image")
    void getPreview_ShouldReturnPreviewImageBytes() {
        // Given
        String ingredientName = "Vodka";
        ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok(new byte[]{});
        when(ingredientService.getPreview(ingredientName)).thenReturn(expectedResponse);

        // When
        ResponseEntity<byte[]> response = ingredientController.getPreview(ingredientName);

        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(ingredientService).getPreview(ingredientName);
    }

    @Test
    @DisplayName("Should download ingredient images and return status")
    void downloadIngredientsImages_ShouldReturnStatusOk() {
        // Given
        doNothing().when(ingredientApiInteractor).downloadImages();

        // When
        ResponseEntity<String> response = ingredientController.downloadIngredientsImages();

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo("Images Ingrédients à jour");
        verify(ingredientApiInteractor).downloadImages();
    }

    @Test
    @DisplayName("Should download ingredient previews and return status")
    void downloadIngredientPreviews_ShouldReturnStatusOk() {
        // Given
        doNothing().when(ingredientApiInteractor).downloadPreviews();

        // When
        ResponseEntity<String> response = ingredientController.downloadIngredientPreviews();

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo("Previews Ingrédients à jour");
        verify(ingredientApiInteractor).downloadPreviews();
    }

    @Test
    @DisplayName("Should return ingredient strings")
    void getIngredientsStrings_ShouldReturnListOfStrings() {
        // Given
        List<String> ingredientStrings = List.of("Vodka", "Gin");
        when(ingredientService.getIngredientsStrings()).thenReturn(ingredientStrings);

        // When
        List<String> result = ingredientController.getIngredientsStrings();

        // Then
        assertThat(result).isEqualTo(ingredientStrings);
        verify(ingredientService).getIngredientsStrings();
    }
}
