package com.jux.juxbar.service;

import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.Ingredient;
import com.jux.juxbar.model.IngredientImage;
import com.jux.juxbar.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private ImageCompressor imageCompressor;

    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should initialize ingredient cache")
    void initCache_ShouldInitializeCache() {
        // When
        ingredientService.initCache();

        // Then
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return all ingredients")
    void getAllIngredients_ShouldReturnAllIngredients() {
        // Given
        List<Ingredient> ingredientList = new ArrayList<>();
        when(ingredientRepository.findAll()).thenReturn(ingredientList);

        // When
        Iterable<Ingredient> result = ingredientService.getAllIngredients();

        // Then
        assertThat(result).isEqualTo(ingredientList);
        verify(ingredientRepository).findAll();
    }

    @Test
    @DisplayName("Should return all ingredients with pagination")
    void getAllIngredients_WithPagination_ShouldReturnPagedIngredients() {
        // Given
        Pageable pageable = PageRequest.of(1, 10);
        Page<Ingredient> mockIngredients = mock(Page.class);
        when(ingredientRepository.findAll(pageable)).thenReturn(mockIngredients);

        // When
        Iterable<Ingredient> result = ingredientService.getAllIngredients(pageable);

        // Then
        assertThat(result).isEqualTo(mockIngredients);
        verify(ingredientRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should return ingredient by id")
    void getIngredient_ShouldReturnIngredientById() {
        // Given
        int id = 1;
        Ingredient ingredient = new Ingredient();
        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));

        // When
        Optional<Ingredient> result = ingredientService.getIngredient(id);

        // Then
        assertThat(result).contains(ingredient);
        verify(ingredientRepository).findById(id);
    }

    @Test
    @DisplayName("Should save ingredient")
    void saveIngredient_ShouldSaveIngredient() {
        // Given
        Ingredient ingredient = new Ingredient();

        // When
        ingredientService.saveIngredient(ingredient);

        // Then
        verify(ingredientRepository).save(ingredient);
    }

    @Test
    @DisplayName("Should return ingredient by name")
    void getIngredientByName_ShouldReturnIngredientByName() {
        // Given
        String name = "Salt";
        Ingredient ingredient = new Ingredient();
        when(ingredientRepository.findByStrIngredient(name)).thenReturn(Optional.of(ingredient));

        // When
        Optional<Ingredient> result = ingredientService.getIngredientByName(name);

        // Then
        assertThat(result).contains(ingredient);
        verify(ingredientRepository).findByStrIngredient(name);
    }

    @Test
    @DisplayName("Should return ingredient strings")
    void getIngredientsStrings_ShouldReturnListOfStrings() {
        // Given
        List<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setStrIngredient("Salt");
        ingredientList.add(ingredient);
        when(ingredientRepository.findAll()).thenReturn(ingredientList);

        // When
        List<String> result = ingredientService.getIngredientsStrings();

        // Then
        assertThat(result).contains("Salt");
        verify(ingredientRepository).findAll();
    }

    @Test
    @DisplayName("Should return image for an ingredient")
    void getImage_ShouldReturnIngredientImage() throws Exception {
        // Given
        String ingredientName = "Salt";
        Ingredient ingredient = new Ingredient();
        IngredientImage imageData = new IngredientImage();
        imageData.setImage(new byte[]{});
        ingredient.setImageData(imageData);

        when(ingredientRepository.findByStrIngredient(ingredientName)).thenReturn(Optional.of(ingredient));
        when(imageCompressor.compress(any(byte[].class), eq("png"), eq(0.4))).thenReturn(new byte[]{});

        // When
        ResponseEntity<byte[]> response = ingredientService.getImage(ingredientName);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok().build().getStatusCode());
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.IMAGE_PNG);
        verify(ingredientRepository).findByStrIngredient(ingredientName);
    }

    @Test
    @DisplayName("Should return preview image for an ingredient")
    void getPreview_ShouldReturnIngredientPreview() throws Exception {
        // Given
        String ingredientName = "Salt";
        Ingredient ingredient = new Ingredient();
        IngredientImage imageData = new IngredientImage();
        imageData.setPreview(new byte[]{});
        ingredient.setImageData(imageData);

        when(ingredientRepository.findByStrIngredient(ingredientName)).thenReturn(Optional.of(ingredient));
        when(imageCompressor.compress(any(byte[].class), eq("png"), eq(0.2))).thenReturn(new byte[]{});

        // When
        ResponseEntity<byte[]> response = ingredientService.getPreview(ingredientName);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok().build().getStatusCode());
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.IMAGE_PNG);
        verify(ingredientRepository).findByStrIngredient(ingredientName);
    }

}
