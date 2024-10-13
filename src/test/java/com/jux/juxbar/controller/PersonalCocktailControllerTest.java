package com.jux.juxbar.controller;

import com.jux.juxbar.component.TextSanitizer;
import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.service.PersonalCocktailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PersonalCocktailControllerTest {

    @Mock
    private PersonalCocktailService personalCocktailService;

    @Mock
    private Principal principal;

    @InjectMocks
    private PersonalCocktailController personalCocktailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return personal cocktails for logged-in user")
    void getPersonalCocktails_ShouldReturnPersonalCocktails() {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        List<PersonalCocktail> personalCocktails = List.of(new PersonalCocktail(), new PersonalCocktail());
        when(personalCocktailService.getPersonalCocktails(username)).thenReturn(personalCocktails);

        // When
        Iterable<PersonalCocktail> result = personalCocktailController.getPersonalCocktails(principal);

        // Then
        assertThat(result).isEqualTo(personalCocktails);
        verify(personalCocktailService).getPersonalCocktails(username);
    }

    @Test
    @DisplayName("Should return personal cocktails for specified username")
    void getPersonalCocktails_ByUsername_ShouldReturnPersonalCocktails() {
        // Given
        String username = "testUser";
        List<PersonalCocktail> personalCocktails = List.of(new PersonalCocktail(), new PersonalCocktail());
        when(personalCocktailService.getPersonalCocktails(username)).thenReturn(personalCocktails);

        // When
        Iterable<PersonalCocktail> result = personalCocktailController.getPersonalCocktails(username);

        // Then
        assertThat(result).isEqualTo(personalCocktails);
        verify(personalCocktailService).getPersonalCocktails(username);
    }

    @Test
    @DisplayName("Should return personal cocktail image by ID")
    void getPersonalCocktailImage_ShouldReturnImageBytes() {
        // Given
        int cocktailId = 1;
        byte[] imageBytes = new byte[]{};
        when(personalCocktailService.getPersonalCocktailImage(cocktailId)).thenReturn(imageBytes);

        // When
        ResponseEntity<byte[]> response = personalCocktailController.getPersonalCocktailImage(cocktailId);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(imageBytes);
        verify(personalCocktailService).getPersonalCocktailImage(cocktailId);
    }

    @Test
    @DisplayName("Should save personal cocktail image and return success message")
    void savePersonalCocktailImage_ShouldReturnSuccessMessage() throws IOException {
        // Given
        String cocktailName = "Mojito";
        byte[] imageBytes = new byte[]{};
        String expectedResponse = "Image saved successfully";
        when(personalCocktailService.savePersonalCocktailImage(cocktailName, imageBytes)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = personalCocktailController.savePersonalCocktailImage(cocktailName, imageBytes);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(personalCocktailService).savePersonalCocktailImage(cocktailName, imageBytes);
    }

    @Test
    @DisplayName("Should save personal cocktail and return success message")
    void savePersonalCocktail_ShouldReturnSuccessMessage() {
        // Given
        PersonalCocktail personalCocktail = new PersonalCocktail();
        personalCocktail.setStrIngredient1("any");
        String expectedResponse = "Saved";

        try (MockedStatic<TextSanitizer> textSanitizerMockedStatic = mockStatic(TextSanitizer.class)){
            textSanitizerMockedStatic.when(() -> TextSanitizer.sanitizeCocktailText(personalCocktail)).thenReturn(personalCocktail);
            when(personalCocktailService.savePersonalCocktail(any(PersonalCocktail.class))).thenReturn(expectedResponse);



        // When
        ResponseEntity<String> response = personalCocktailController.savePersonalCocktail(personalCocktail);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(personalCocktailService).savePersonalCocktail(any(PersonalCocktail.class));
        }
    }

    @Test
    @DisplayName("Should return personal cocktail by ID for logged-in user")
    void getPersonalCocktail_ShouldReturnPersonalCocktailById() {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        PersonalCocktail personalCocktail = new PersonalCocktail();
        when(personalCocktailService.getPersonalCocktail(1, username)).thenReturn(personalCocktail);

        // When
        PersonalCocktail result = personalCocktailController.getPersonalCocktail(1, principal);

        // Then
        assertThat(result).isEqualTo(personalCocktail);
        verify(personalCocktailService).getPersonalCocktail(1, username);
    }

    @Test
    @DisplayName("Should remove personal cocktail and return success message")
    void removePersonalCocktail_ShouldReturnSuccessMessage() {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        String expectedResponse = "Cocktail removed successfully";
        when(personalCocktailService.removePersonalCocktail(1, username)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = personalCocktailController.removePersonalCocktail(1, principal);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(personalCocktailService).removePersonalCocktail(1, username);
    }

    @Test
    @DisplayName("Should trash personal cocktail and return success message")
    void trashPersonalCocktail_ShouldReturnSuccessMessage() {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        String expectedResponse = "Cocktail trashed successfully";
        when(personalCocktailService.trashPersonalCocktail(1, username)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = personalCocktailController.trashPersonalCocktail(1, principal);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(personalCocktailService).trashPersonalCocktail(1, username);
    }
}
