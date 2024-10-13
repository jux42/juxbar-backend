package com.jux.juxbar.controller;

import com.jux.juxbar.component.SoftDrinkApiInteractor;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.service.SoftDrinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SoftDrinkControllerTest {

    @Mock
    private SoftDrinkService softDrinkService;

    @Mock
    private SoftDrinkApiInteractor softDrinkApiInteractor;

    @InjectMocks
    private SoftDrinkController softDrinkController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return paginated list of soft drinks")
    void getSoftDrinks_withPagination_shouldReturnPaginatedSoftDrinks() {
        // Given
        Pageable pageable = PageRequest.of(1, 10);
        Iterable<SoftDrink> mockSoftDrinks = mock(Iterable.class);
        when(softDrinkService.getDrinks(eq(pageable))).thenReturn(mockSoftDrinks);

        // When
        ResponseEntity<Iterable<SoftDrink>> response = softDrinkController.getSoftDrinks(1, 10);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockSoftDrinks);
        verify(softDrinkService).getDrinks(eq(pageable));
    }

    @Test
    @DisplayName("should return full list of soft drinks")
    void getSoftDrinks_withoutPagination_shouldReturnAllSoftDrinks() {
        // Given
        Iterable<SoftDrink> mockSoftDrinks = mock(Iterable.class);
        when(softDrinkService.getAllDrinks()).thenReturn(mockSoftDrinks);

        // When
        ResponseEntity<Iterable<SoftDrink>> response = softDrinkController.getSoftDrinks(null, null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockSoftDrinks);
        verify(softDrinkService).getAllDrinks();
    }

    @Test
    @DisplayName("should return one soft drink with wanted ID")
    void getSoftDrink_shouldReturnSoftDrinkById() {
        // Given
        int softDrinkId = 1;
        Optional<SoftDrink> mockSoftDrink = Optional.of(mock(SoftDrink.class));
        when(softDrinkService.getDrink(softDrinkId)).thenReturn(mockSoftDrink);

        // When
        Optional<SoftDrink> result = softDrinkController.getSoftDrink(softDrinkId);

        // Then
        assertThat(result).isEqualTo(mockSoftDrink);
        verify(softDrinkService).getDrink(softDrinkId);
    }

    @Test
    @DisplayName("should return soft drink Image")
    void getImage_shouldReturnImageBytesWithHeaders() {
        // Given
        int softDrinkId = 1;
        byte[] mockImage = new byte[]{};
        when(softDrinkService.getImage(softDrinkId)).thenReturn(mockImage);

        // When
        ResponseEntity<byte[]> response = softDrinkController.getImage(softDrinkId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get(HttpHeaders.CACHE_CONTROL)).contains("public, max-age=31536000");
        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("image/jpeg");
        assertThat(response.getBody()).isEqualTo(mockImage);
        verify(softDrinkService).getImage(softDrinkId);
    }

    @Test
    @DisplayName("should return soft drink Preview")
    void getPreview_shouldReturnPreviewBytesWithHeaders() {
        // Given
        int softDrinkId = 1;
        byte[] mockPreview = new byte[]{};
        when(softDrinkService.getPreview(softDrinkId)).thenReturn(mockPreview);

        // When
        ResponseEntity<byte[]> response = softDrinkController.getPreview(softDrinkId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get(HttpHeaders.CACHE_CONTROL)).contains("public, max-age=31536000");
        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains("image/jpeg");
        assertThat(response.getBody()).isEqualTo(mockPreview);
        verify(softDrinkService).getPreview(softDrinkId);
    }

    @Test
    @DisplayName("download method should call external API interactor")
    void downloadSoftDrinks_shouldCallSoftDrinkApiInteractor() throws InterruptedException {
        // When
        ResponseEntity<String> response = softDrinkController.downloadSoftDrinks();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Soft drinks à jour");
        verify(softDrinkApiInteractor).checkUpdateAndDownload();
    }

    @Test
    @DisplayName("download method for images should call external API interactor")
    void downloadSoftDrinkImages_shouldCallSoftDrinkApiInteractor() {
        // When
        ResponseEntity<String> response = softDrinkController.downloadSoftDrinksImages();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Images Soft à jour");
        verify(softDrinkApiInteractor).downloadImages();
    }

    @Test
    @DisplayName("download method for previews should call external API interactor")
    void downloadSoftDrinkPreviews_shouldCallSoftDrinkApiInteractor() {
        // When
        ResponseEntity<String> response = softDrinkController.downloadSoftDrinkPreviews();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Previews Soft à jour");
        verify(softDrinkApiInteractor).downloadPreviews();
    }

    @Test
    @DisplayName("should return size of soft drinks List")
    void getSoftDrinksArraySize_shouldReturnCorrectSize() {
        // Given
        Iterable<SoftDrink> mockSoftDrinks = new ArrayList<>(100);
        long expectedSize = mockSoftDrinks.spliterator().getExactSizeIfKnown();

        when(softDrinkService.getAllDrinks()).thenReturn(mockSoftDrinks);

        // When
        ResponseEntity<Long> response = softDrinkController.getSoftDrinksArraySize();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedSize);
        verify(softDrinkService).getAllDrinks();
    }
}
