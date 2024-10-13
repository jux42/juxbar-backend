package com.jux.juxbar.service;

import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.repository.JuxBarUserRepository;
import com.jux.juxbar.repository.PersonalCocktailImageRepository;
import com.jux.juxbar.repository.PersonalCocktailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @Mock
    private JuxBarUserRepository juxBarUserRepository;

    @Mock
    private PersonalCocktailRepository personalCocktailRepository;

    @Mock
    private PersonalCocktailImageRepository personalCocktailImageRepository;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return current user by username")
    void getCurrentUser_ShouldReturnUser() {
        // Given
        String username = "testUser";
        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(username);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(juxBarUser);

        // When
        JuxBarUser result = profileService.getCurrentUser(username);

        // Then
        assertThat(result).isEqualTo(juxBarUser);
        verify(juxBarUserRepository).findByUsername(username);
    }

    @Test
    @DisplayName("Should return profile picture by username")
    void getProfilePicture_ShouldReturnProfilePicture() {
        // Given
        String username = "testUser";
        byte[] profilePicture = new byte[]{1, 2, 3};
        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setProfilePicture(profilePicture);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(juxBarUser);

        // When
        byte[] result = profileService.getProfilePicture(username);

        // Then
        assertThat(result).isEqualTo(profilePicture);
    }

    @Test
    @DisplayName("Should update 'About Me' text for user")
    void updateAboutMeText_ShouldUpdateAboutMe() {
        // Given
        String username = "testUser";
        String aboutMeText = "This is my about me text";
        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(username);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(juxBarUser);

        // When
        String result = profileService.updateAboutMeText(username, aboutMeText);

        // Then
        assertThat(result).isEqualTo("'about me' section updated");
        assertThat(juxBarUser.getAboutMeText()).isEqualTo(aboutMeText);
        verify(juxBarUserRepository).save(juxBarUser);
    }

    @Test
    @DisplayName("Should update profile picture")
    void updateProfilePicture_ShouldUpdateProfilePicture() throws IOException {
        // Given
        String username = "testUser";
        byte[] picture = new byte[]{4, 5, 6};
        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(username);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(juxBarUser);

        // When
        String result = profileService.updateProfilePicture(username, picture);

        // Then
        assertThat(result).isEqualTo("profile picture updated");
        assertThat(juxBarUser.getProfilePicture()).isEqualTo(picture);
        verify(juxBarUserRepository).save(juxBarUser);
    }

    @Test
    @DisplayName("Should delete user and associated personal cocktails")
    void deleteAccount_ShouldDeleteUserAndCocktails() {
        // Given
        String username = "testUser";
        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(username);
        PersonalCocktail personalCocktail1 = new PersonalCocktail();
        PersonalCocktail personalCocktail2 = new PersonalCocktail();
        List<PersonalCocktail> personalCocktails = Arrays.asList(personalCocktail1, personalCocktail2);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(juxBarUser);
        when(personalCocktailRepository.findByOwnerName(username)).thenReturn(personalCocktails);

        // When
        String result = profileService.deleteAccount(username);

        // Then
        assertThat(result).isEqualTo("User deleted... bye bye");
        verify(personalCocktailRepository).deleteAll(personalCocktails);
        verify(juxBarUserRepository).delete(juxBarUser);
        verify(personalCocktailImageRepository, times(2)).delete(any());
    }
}
