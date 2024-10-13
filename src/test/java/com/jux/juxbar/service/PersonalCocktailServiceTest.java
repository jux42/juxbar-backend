package com.jux.juxbar.service;

import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.model.PersonalCocktailImage;
import com.jux.juxbar.model.State;
import com.jux.juxbar.repository.JuxBarUserRepository;
import com.jux.juxbar.repository.PersonalCocktailImageRepository;
import com.jux.juxbar.repository.PersonalCocktailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonalCocktailServiceTest {

    @Mock
    private PersonalCocktailRepository personalCocktailRepository;

    @Mock
    private JuxBarUserRepository juxBarUserRepository;

    @Mock
    private PersonalCocktailImageRepository personalCocktailImageRepository;

    @InjectMocks
    private PersonalCocktailService personalCocktailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return personal cocktails by owner name")
    void getPersonalCocktails_ShouldReturnPersonalCocktails() {
        // Given
        String ownerName = "testUser";
        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(ownerName);

        PersonalCocktail cocktail = new PersonalCocktail();
        when(juxBarUserRepository.findByUsername(ownerName)).thenReturn(juxBarUser);
        when(personalCocktailRepository.findByOwnerName(ownerName)).thenReturn(Arrays.asList(cocktail));

        // When
        Iterable<PersonalCocktail> result = personalCocktailService.getPersonalCocktails(ownerName);

        // Then
        assertThat(result).contains(cocktail);
        verify(personalCocktailRepository).findByOwnerName(ownerName);
    }

    @Test
    @DisplayName("Should save a personal cocktail")
    void savePersonalCocktail_ShouldSavePersonalCocktail() {
        // Given
        PersonalCocktail cocktail = new PersonalCocktail();
        cocktail.setStrDrink("Test Cocktail");

        // When
        String result = personalCocktailService.savePersonalCocktail(cocktail);

        // Then
        assertThat(result).isEqualTo("Saved");
        assertThat(cocktail.getState()).isEqualTo(State.SHOWED);
        verify(personalCocktailRepository).save(cocktail);
    }

    @Test
    @DisplayName("Should save personal cocktail image")
    void savePersonalCocktailImage_ShouldSaveImage() {
        // Given
        String cocktailName = "Test Cocktail";
        byte[] imageBytes = new byte[]{1, 2, 3};
        PersonalCocktail cocktail = new PersonalCocktail();
        when(personalCocktailRepository.findByStrDrink(cocktailName)).thenReturn(Optional.of(cocktail));

        // When
        String result = personalCocktailService.savePersonalCocktailImage(cocktailName, imageBytes);

        // Then
        assertThat(result).isEqualTo("custom cocktail picture updated");
        assertThat(cocktail.getImageData().getImage()).isEqualTo(imageBytes);
        verify(personalCocktailRepository).save(cocktail);
    }

    @Test
    @DisplayName("Should return personal cocktail by id and username")
    void getPersonalCocktail_ShouldReturnCocktailByIdAndUsername() {
        // Given
        String username = "testUser";
        PersonalCocktail cocktail = new PersonalCocktail();
        cocktail.setId(1);
        when(personalCocktailRepository.findByOwnerName(username)).thenReturn(Arrays.asList(cocktail));

        // When
        PersonalCocktail result = personalCocktailService.getPersonalCocktail(1, username);

        // Then
        assertThat(result).isEqualTo(cocktail);
    }

    @Test
    @DisplayName("Should return personal cocktail image")
    void getPersonalCocktailImage_ShouldReturnImage() {
        // Given
        int id = 1;
        byte[] imageBytes = new byte[]{1, 2, 3};
        PersonalCocktail cocktail = new PersonalCocktail();
        PersonalCocktailImage image = new PersonalCocktailImage();
        image.setImage(imageBytes);
        cocktail.setImageData(image);
        when(personalCocktailRepository.findById(id)).thenReturn(Optional.of(cocktail));

        // When
        byte[] result = personalCocktailService.getPersonalCocktailImage(id);

        // Then
        assertThat(result).isEqualTo(imageBytes);
    }

    @Test
    @DisplayName("Should remove personal cocktail by id and username")
    void removePersonalCocktail_ShouldRemoveCocktail() {
        // Given
        String username = "testUser";
        PersonalCocktail cocktail = new PersonalCocktail();
        cocktail.setId(1);
        when(personalCocktailRepository.findByOwnerName(username)).thenReturn(Arrays.asList(cocktail));

        // When
        String result = personalCocktailService.removePersonalCocktail(1, username);

        // Then
        assertThat(result).isEqualTo("suppression effectuée");
        verify(personalCocktailRepository).delete(cocktail);
    }

    @Test
    @DisplayName("Should trash personal cocktail by id and username")
    void trashPersonalCocktail_ShouldTrashCocktail() {
        // Given
        String username = "testUser";
        PersonalCocktail cocktail = new PersonalCocktail();
        cocktail.setId(1);
        when(personalCocktailRepository.findByOwnerName(username)).thenReturn(Arrays.asList(cocktail));

        // When
        String result = personalCocktailService.trashPersonalCocktail(1, username);

        // Then
        assertThat(result).isEqualTo("mise en corbeille effectuée");
        assertThat(cocktail.getState()).isEqualTo(State.TRASHED);
        verify(personalCocktailRepository).save(cocktail);
    }
}
