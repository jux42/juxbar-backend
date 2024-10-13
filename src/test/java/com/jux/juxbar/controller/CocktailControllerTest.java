package com.jux.juxbar.controller;

import com.jux.juxbar.component.CocktailApiInteractor;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.service.CocktailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CocktailControllerTest {

    @Mock
    private CocktailService cocktailService;

    @Mock
    private CocktailApiInteractor cocktailApiInteractor;

    @InjectMocks
    private CocktailController cocktailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return paginated list of cocktails")
    void getCocktails_withPagination_shouldReturnPaginatedSoftDrinks() {
        // Given
        Pageable pageable = PageRequest.of(1, 10);
        Page<Cocktail> mockCocktails = mock(Page.class);
        when(cocktailService.getDrinks(eq(pageable))).thenReturn(mockCocktails);

        // When
        ResponseEntity<Iterable<Cocktail>> response = cocktailController.getCocktails(1, 10);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockCocktails);
        verify(cocktailService).getDrinks(eq(pageable));
    }


    @Test
    @DisplayName("should return full list of cocktails")
    void getCocktails_withoutPagination_shouldReturnAllCocktails() {
        // Given
        Iterable<Cocktail> mockCocktails = mock(Iterable.class);
        when(cocktailService.getAllDrinks()).thenReturn(mockCocktails);

        // When
        ResponseEntity<Iterable<Cocktail>> response = cocktailController.getCocktails(null, null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockCocktails);
        verify(cocktailService).getAllDrinks();
    }

    @Test
    @DisplayName("should return one cocktail with wanted ID")
    void getCocktail_shouldReturnCocktailById() {
        // Given
        int cocktailId = 1;
        Optional<Cocktail> mockCocktail = Optional.of(mock(Cocktail.class));
        when(cocktailService.getDrink(cocktailId)).thenReturn(mockCocktail);

        // When
        Optional<Cocktail> result = cocktailController.getCocktail(cocktailId);

        // Then
        assertThat(result).isEqualTo(mockCocktail);
        verify(cocktailService).getDrink(cocktailId);
    }

    @Test
    @DisplayName("should return cocktail Image")
    void getImage_shouldReturnImageBytesWithHeaders() {
        // Given
        int cocktailId = 1;
        byte[] mockImage = "testImage".getBytes();
        when(cocktailService.getImage(cocktailId)).thenReturn(mockImage);

        // When
        ResponseEntity<byte[]> response = cocktailController.getImage(cocktailId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get(HttpHeaders.CACHE_CONTROL)).contains("public, max-age=31536000");
        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("image/jpeg");
        assertThat(response.getBody()).isEqualTo(mockImage);
        verify(cocktailService).getImage(cocktailId);
    }

    @Test
    @DisplayName("should return cocktail Preview")
    void getPreview_shouldReturnPreviewBytesWithHeaders() {
        // Given
        int cocktailId = 1;
        byte[] mockPreview = "testPreview".getBytes();
        when(cocktailService.getPreview(cocktailId)).thenReturn(mockPreview);

        // When
        ResponseEntity<byte[]> response = cocktailController.getPreview(cocktailId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get(HttpHeaders.CACHE_CONTROL)).contains("public, max-age=31536000");
        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("image/jpeg");
        assertThat(response.getBody()).isEqualTo(mockPreview);
        verify(cocktailService).getPreview(cocktailId);
    }

    @Test
    @DisplayName("download method should call external API interactor")
    void downloadCocktails_shouldCallCocktailApiInteractor() throws InterruptedException {
        // When
        ResponseEntity<String> response = cocktailController.downloadCocktails();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Cocktails à jour");
        verify(cocktailApiInteractor).checkUpdateAndDownload();
    }

    @Test
    @DisplayName("download method for images should call external API interactor")
    void downloadCocktailImages_shouldCallCocktailApiInteractor() {
        // When
        ResponseEntity<String> response = cocktailController.downloadCocktailsImages();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Images Cocktails à jour");
        verify(cocktailApiInteractor).downloadImages();
    }

    @Test
    @DisplayName("download method for previews should call external API interactor")
    void downloadCocktailPreviews_shouldCallCocktailApiInteractor() {
        // When
        ResponseEntity<String> response = cocktailController.downloadCocktailsPreviews();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Previews Cocktails à jour");
        verify(cocktailApiInteractor).downloadPreviews();
    }

    @Test
    @DisplayName("should return size of cocktails List")
    void getCocktailsArraySize_shouldReturnCorrectSize() {
        // Given
        Iterable<Cocktail> mockCocktails = new ArrayList<>(100);
        long expectedSize = mockCocktails.spliterator().getExactSizeIfKnown();

        when(cocktailService.getAllDrinks()).thenReturn(mockCocktails);

        // When
        ResponseEntity<Long> response = cocktailController.getCocktailsArraySize();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedSize);
        verify(cocktailService).getAllDrinks();
    }
}
