package com.jux.juxbar.service;

import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.repository.PersonalCocktailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jux.juxbar.model.State.SHOWED;
import static com.jux.juxbar.model.State.TRASHED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdminOperationsServiceTest {

    @Mock
    private PersonalCocktailRepository personalCocktailRepository;

    @InjectMocks
    private AdminOperationsService adminOperationsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return list of trashed cocktail IDs for a user")
    void listTrashedCocktailsOfUser_ShouldReturnListOfTrashedCocktailIds() {
        // Given
        String username = "testUser";
        PersonalCocktail cocktail1 = new PersonalCocktail();
        cocktail1.setId(1);
        cocktail1.setState(TRASHED);

        PersonalCocktail cocktail2 = new PersonalCocktail();
        cocktail2.setId(2);
        cocktail2.setState(TRASHED);

        when(personalCocktailRepository.findByOwnerNameAndState(username, TRASHED)).thenReturn(List.of(cocktail1, cocktail2));

        // When
        List<Integer> trashedCocktails = adminOperationsService.listTrashedCocktailsOfUser(username);

        // Then
        assertThat(trashedCocktails).containsExactly(1, 2);
        verify(personalCocktailRepository).findByOwnerNameAndState(username, TRASHED);
    }

    @Test
    @DisplayName("Should restore one trashed cocktail")
    void restoreOneTrashedCocktail_ShouldRestoreCocktail_WhenFoundInTrash() {
        // Given
        String username = "testUser";
        Integer cocktailId = 1;
        PersonalCocktail trashedCocktail = new PersonalCocktail();
        trashedCocktail.setId(cocktailId);
        trashedCocktail.setState(TRASHED);

        when(personalCocktailRepository.findById(cocktailId)).thenReturn(Optional.of(trashedCocktail));

        // When
        String result = adminOperationsService.restoreOneTrashedCocktail(username, cocktailId);

        // Then
        assertThat(result).isEqualTo("cocktail successfully restored");
        assertThat(trashedCocktail.getState()).isEqualTo(SHOWED);
        verify(personalCocktailRepository).save(trashedCocktail);
    }

    @Test
    @DisplayName("Should return error message if cocktail not found in trash")
    void restoreOneTrashedCocktail_ShouldReturnError_WhenNotFoundInTrash() {
        // Given
        String username = "testUser";
        Integer cocktailId = 1;

        when(personalCocktailRepository.findById(cocktailId)).thenReturn(Optional.empty());

        // When
        String result = adminOperationsService.restoreOneTrashedCocktail(username, cocktailId);

        // Then
        assertThat(result).isEqualTo("this cocktail was not found in the trashcan");
        verify(personalCocktailRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should trash a cocktail")
    void trashOnePersonalCocktail_ShouldTrashCocktail_WhenFoundAndShown() {
        // Given
        String username = "testUser";
        Integer cocktailId = 1;
        PersonalCocktail cocktail = new PersonalCocktail();
        cocktail.setId(cocktailId);
        cocktail.setState(SHOWED);

        when(personalCocktailRepository.findById(cocktailId)).thenReturn(Optional.of(cocktail));

        // When
        String result = adminOperationsService.trashOnePersonalCocktail(username, cocktailId);

        // Then
        assertThat(result).isEqualTo("cocktail successfully trashed");
        assertThat(cocktail.getState()).isEqualTo(TRASHED);
        verify(personalCocktailRepository).save(cocktail);
    }

    @Test
    @DisplayName("Should return error message if cocktail is not found")
    void trashOnePersonalCocktail_ShouldReturnError_WhenCocktailNotFound() {
        // Given
        String username = "testUser";
        Integer cocktailId = 1;

        when(personalCocktailRepository.findById(cocktailId)).thenReturn(Optional.empty());

        // When
        String result = adminOperationsService.trashOnePersonalCocktail(username, cocktailId);

        // Then
        assertThat(result).isEqualTo("this cocktail was not found");
        verify(personalCocktailRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should restore all trashed cocktails of a user")
    void restoreAllCocktailsOfUser_ShouldRestoreAllCocktails() {
        // Given
        String username = "testUser";
        PersonalCocktail cocktail1 = new PersonalCocktail();
        cocktail1.setId(1);
        cocktail1.setState(TRASHED);

        PersonalCocktail cocktail2 = new PersonalCocktail();
        cocktail2.setId(2);
        cocktail2.setState(TRASHED);

        when(personalCocktailRepository.findByOwnerNameAndState(username, TRASHED)).thenReturn(List.of(cocktail1, cocktail2));

        // When
        String result = adminOperationsService.restoreAllCocktailsOfUser(username);

        // Then
        assertThat(result).isEqualTo("all cocktails successfully restored");
        assertThat(cocktail1.getState()).isEqualTo(SHOWED);
        assertThat(cocktail2.getState()).isEqualTo(SHOWED);
        verify(personalCocktailRepository, times(2)).save(any(PersonalCocktail.class));
    }

    @Test
    @DisplayName("Should return error message if no cocktails found in trash")
    void restoreAllCocktailsOfUser_ShouldReturnError_WhenNoCocktailsInTrash() {
        // Given
        String username = "testUser";

        when(personalCocktailRepository.findByOwnerNameAndState(username, TRASHED)).thenReturn(new ArrayList<>());

        // When
        String result = adminOperationsService.restoreAllCocktailsOfUser(username);

        // Then
        assertThat(result).isEqualTo("no cocktail found in the trashcan");
        verify(personalCocktailRepository, never()).save(any());
    }
}
