package com.jux.juxbar.component;

import com.jux.juxbar.model.PersonalCocktail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TextSanitizerTest {

    @Test
    @DisplayName("Should sanitize text and remove unwanted HTML")
    void sanitizeText_ShouldRemoveUnwantedHTML() {
        // Given
        String inputText = "<script>alert('hack');</script><p>Safe text</p>";

        // When
        String sanitizedText = TextSanitizer.sanitizeText(inputText);

        // Then
        assertThat(sanitizedText).isEqualTo("Safe text");
    }

    @Test
    @DisplayName("Should return true if text is secure with no unsafe content")
    void securize_ShouldReturnTrue_WhenTextIsSafe() {
        // Given
        String safeText = "This is a safe text";

        // When
        boolean isSecure = TextSanitizer.securize(safeText);

        // Then
        assertThat(isSecure).isTrue();
    }

    @Test
    @DisplayName("Should return false if text contains unsafe content")
    void securize_ShouldReturnFalse_WhenTextIsUnsafe() {
        // Given
        String unsafeText = "<script>alert('hack');</script>";

        // When
        boolean isSecure = TextSanitizer.securize(unsafeText);

        // Then
        assertThat(isSecure).isFalse();
    }

    @Test
    @DisplayName("Should sanitize all ingredients in PersonalCocktail")
    void sanitizeCocktailText_ShouldSanitizeIngredients() {
        // Given
        PersonalCocktail cocktail = new PersonalCocktail();
        cocktail.setStrIngredient1("<script>alert('hack');</script>Vodka");
        cocktail.setStrIngredient2("<p>Orange juice</p>");
        cocktail.setStrIngredient3("");
        cocktail.setStrIngredient4("Ice");
        cocktail.setStrIngredient5("<img src='image.jpg'>");

        // When
        PersonalCocktail sanitizedCocktail = TextSanitizer.sanitizeCocktailText(cocktail);

        // Then
        assertThat(sanitizedCocktail.getStrIngredient1()).isEqualTo("Vodka");
        assertThat(sanitizedCocktail.getStrIngredient2()).isEqualTo("Orange juice");
        assertThat(sanitizedCocktail.getStrIngredient3()).isEqualTo("");
        assertThat(sanitizedCocktail.getStrIngredient4()).isEqualTo("Ice");
        assertThat(sanitizedCocktail.getStrIngredient5()).isEqualTo("");
        assertThat(sanitizedCocktail.getStrIngredient6()).isEqualTo("");
    }
}
