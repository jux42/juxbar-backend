package com.jux.juxbar.service;

import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.model.SoftDrinkImage;
import com.jux.juxbar.repository.SoftDrinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SoftDrinkServiceTest {

    @Mock
    private SoftDrinkRepository softDrinkRepository;

    @Mock
    private ImageCompressor imageCompressor;

    @InjectMocks
    private SoftDrinkService softDrinkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return SoftDrink by idDrink")
    void getDrinkByIdDrink_ShouldReturnSoftDrink() {
        // Given
        String idDrink = "12345";
        SoftDrink softDrink = new SoftDrink();
        softDrink.setIdDrink(idDrink);
        when(softDrinkRepository.findByIdDrink(idDrink)).thenReturn(Optional.of(softDrink));

        // When
        Optional<SoftDrink> result = softDrinkService.getDrinkByIdDrink(idDrink);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getIdDrink()).isEqualTo(idDrink);
        verify(softDrinkRepository).findByIdDrink(idDrink);
    }

    @Test
    @DisplayName("Should save SoftDrink")
    void saveDrink_ShouldSaveSoftDrink() {
        // Given
        SoftDrink softDrink = new SoftDrink();
        softDrink.setStrDrink("Coca-Cola");

        // When
        softDrinkService.saveDrink(softDrink);

        // Then
        verify(softDrinkRepository).save(softDrink);
    }

    @Test
    @DisplayName("Should return SoftDrink by id")
    void getDrink_ShouldReturnSoftDrink() {
        // Given
        int id = 1;
        SoftDrink softDrink = new SoftDrink();
        softDrink.setId(id);
        when(softDrinkRepository.findById(id)).thenReturn(Optional.of(softDrink));

        // When
        Optional<SoftDrink> result = softDrinkService.getDrink(id);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        verify(softDrinkRepository).findById(id);
    }

    @Test
    @DisplayName("Should return SoftDrink without using cache")
    void getDrinkNoCache_ShouldReturnSoftDrinkWithoutCache() {
        // Given
        int id = 1;
        SoftDrink softDrink = new SoftDrink();
        softDrink.setId(id);
        when(softDrinkRepository.findById(id)).thenReturn(Optional.of(softDrink));

        // When
        Optional<SoftDrink> result = softDrinkService.getDrinkNoCache(id);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        verify(softDrinkRepository).findById(id);
    }

    @Test
    @DisplayName("Should return all SoftDrinks")
    void getAllDrinks_ShouldReturnAllSoftDrinks() {
        // Given
        List<SoftDrink> softDrinks = mock(List.class);
        when(softDrinkRepository.findAll()).thenReturn(softDrinks);

        // When
        Iterable<SoftDrink> result = softDrinkService.getAllDrinks();

        // Then
        assertThat(result).isEqualTo(softDrinks);
        verify(softDrinkRepository).findAll();
    }

    @Test
    @DisplayName("Should return image for SoftDrink by id")
    void getImage_ShouldReturnImage() throws IOException {
        // Given
        int id = 1;
        byte[] imageBytes = new byte[]{1, 2, 3};
        SoftDrink softDrink = new SoftDrink();
        SoftDrinkImage softDrinkImage = new SoftDrinkImage();
        softDrink.setImageData(softDrinkImage);
        softDrinkImage.setId(id);
        softDrinkImage.setImage(imageBytes);
        when(softDrinkRepository.findById(id)).thenReturn(Optional.of(softDrink));
        when(imageCompressor.compress(imageBytes, "png", 0.4)).thenReturn(imageBytes);

        // When
        byte[] result = softDrinkService.getImage(id);

        // Then
        assertThat(result).isEqualTo(imageBytes);
        verify(imageCompressor).compress(imageBytes, "png", 0.4);
    }

    @Test
    @DisplayName("Should return preview image for SoftDrink by id")
    void getPreview_ShouldReturnPreviewImage() {
        // Given
        int id = 1;
        byte[] previewBytes = new byte[]{4, 5, 6};
        SoftDrink softDrink = new SoftDrink();
        SoftDrinkImage softDrinkImage = new SoftDrinkImage();
        softDrink.setImageData(softDrinkImage);
        softDrinkImage.setId(id);
        softDrinkImage.setPreview(previewBytes);
        softDrink.getImageData().setPreview(previewBytes);
        when(softDrinkRepository.findById(id)).thenReturn(Optional.of(softDrink));

        // When
        byte[] result = softDrinkService.getPreview(id);

        // Then
        assertThat(result).isEqualTo(previewBytes);
    }
}
