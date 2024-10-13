package com.jux.juxbar.service;

import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.CocktailImage;
import com.jux.juxbar.repository.CocktailImageRepository;
import com.jux.juxbar.repository.CocktailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CocktailServiceTest {

    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private ImageCompressor imageCompressor;

    @Mock
    private CocktailImageRepository cocktailImageRepository;

    @InjectMocks
    private CocktailService cocktailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return cocktail by ID")
    void getDrink_ShouldReturnCocktailById() {
        // Given
        int cocktailId = 1;
        Cocktail cocktail = new Cocktail();
        cocktail.setId(cocktailId);

        when(cocktailRepository.findById(cocktailId)).thenReturn(Optional.of(cocktail));

        // When
        Optional<Cocktail> result = cocktailService.getDrink(cocktailId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(cocktail);
        verify(cocktailRepository).findById(cocktailId);
    }

    @Test
    @DisplayName("Should return all drinks")
    void getAllDrinks_ShouldReturnAllDrinks() {
        // Given
        Cocktail cocktail1 = new Cocktail();
        Cocktail cocktail2 = new Cocktail();
        when(cocktailRepository.findAll()).thenReturn(List.of(cocktail1, cocktail2));

        // When
        Iterable<Cocktail> result = cocktailService.getAllDrinks();

        // Then
        assertThat(result).containsExactly(cocktail1, cocktail2);
        verify(cocktailRepository).findAll();
    }

    @Test
    @DisplayName("Should return drinks with pagination")
    void getDrinks_ShouldReturnPagedDrinks() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Cocktail cocktail = new Cocktail();
        Page<Cocktail> page = new PageImpl<>(List.of(cocktail));

        when(cocktailRepository.findAll(pageable)).thenReturn(page);

        // When
        Page<Cocktail> result = cocktailService.getDrinks(pageable);

        // Then
        assertThat(result.getContent()).containsExactly(cocktail);
        verify(cocktailRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should compress and return cocktail image")
    void getImage_ShouldReturnCompressedImage() throws IOException {
        // Given
        int cocktailId = 1;
        CocktailImage cocktailImage = new CocktailImage();
        cocktailImage.setImage(new byte[]{1, 2, 3});
        Cocktail cocktail = new Cocktail();
        cocktail.setId(cocktailId);
        cocktail.setImageData(cocktailImage);

        when(cocktailRepository.findById(cocktailId)).thenReturn(Optional.of(cocktail));
        when(imageCompressor.compress(any(byte[].class), eq("png"), eq(0.4))).thenReturn(new byte[]{4, 5, 6});

        // When
        byte[] result = cocktailService.getImage(cocktailId);

        // Then
        assertThat(result).containsExactly(4, 5, 6);
        verify(imageCompressor).compress(any(byte[].class), eq("png"), eq(0.4));
    }

    @Test
    @DisplayName("Should compress and return cocktail preview image")
    void getPreview_ShouldReturnCompressedPreviewImage() throws IOException {
        // Given
        int cocktailId = 1;
        CocktailImage cocktailImage = new CocktailImage();
        cocktailImage.setImage(new byte[]{1, 2, 3});
        Cocktail cocktail = new Cocktail();
        cocktail.setId(cocktailId);
        cocktail.setImageData(cocktailImage);

        when(cocktailRepository.findById(cocktailId)).thenReturn(Optional.of(cocktail));
        when(imageCompressor.compress(any(byte[].class), eq("png"), eq(0.2))).thenReturn(new byte[]{7, 8, 9});

        // When
        byte[] result = cocktailService.getPreview(cocktailId);

        // Then
        assertThat(result).containsExactly(7, 8, 9);
        verify(imageCompressor).compress(any(byte[].class), eq("png"), eq(0.2));
    }

    @Test
    @DisplayName("Should save a cocktail")
    void saveDrink_ShouldSaveCocktail() {
        // Given
        Cocktail cocktail = new Cocktail();

        // When
        cocktailService.saveDrink(cocktail);

        // Then
        verify(cocktailRepository).save(cocktail);
    }

    @Test
    @DisplayName("Should return empty when cocktail not found by ID")
    void getDrink_ShouldReturnEmpty_WhenCocktailNotFound() {
        // Given
        int cocktailId = 1;

        when(cocktailRepository.findById(cocktailId)).thenReturn(Optional.empty());

        // When
        Optional<Cocktail> result = cocktailService.getDrink(cocktailId);

        // Then
        assertThat(result).isEmpty();
        verify(cocktailRepository).findById(cocktailId);
    }
}
