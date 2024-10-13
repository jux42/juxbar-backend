package com.jux.juxbar.controller;

import com.jux.juxbar.component.TextSanitizer;
import com.jux.juxbar.configuration.CustomUserDetailsService;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.service.JuxBarUserService;
import com.jux.juxbar.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @Mock
    private JuxBarUserService juxBarUserService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private Principal principal;

    @InjectMocks
    private ProfileController profileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return current user details")
    void getCurrentUser_ShouldReturnCurrentUserDetails() {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        JuxBarUser juxBarUser = new JuxBarUser();
        when(profileService.getCurrentUser(username)).thenReturn(juxBarUser);

        // When
        ResponseEntity<JuxBarUser> response = profileController.getCurrentUser(principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(juxBarUser);
        verify(profileService).getCurrentUser(username);
    }

    @Test
    @DisplayName("Should update profile picture and return success message")
    void updateProfilePicture_ShouldReturnSuccessMessage() throws IOException {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        byte[] pictureBytes = new byte[]{};
        String expectedResponse = "Profile picture updated";
        when(profileService.updateProfilePicture(username, pictureBytes)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = profileController.updateProfilePicture(pictureBytes, principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(profileService).updateProfilePicture(username, pictureBytes);
    }

    @Test
    @DisplayName("Should handle IOException when updating profile picture")
    void updateProfilePicture_ShouldHandleIOException() throws IOException {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        byte[] pictureBytes = new byte[]{};
        when(profileService.updateProfilePicture(username, pictureBytes)).thenThrow(new IOException("IO error"));

        // When
        ResponseEntity<String> response = profileController.updateProfilePicture(pictureBytes, principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("Failed to update picture");
    }

    @Test
    @DisplayName("Should update 'About Me' text and return success message")
    void updateAboutMeText_ShouldReturnSuccessMessage() {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        String aboutMe = "This is my about me text";
        String expectedResponse = "About Me updated";

        try(MockedStatic<TextSanitizer> textSanitizerMockedStatic = mockStatic(TextSanitizer.class)){
            textSanitizerMockedStatic.when(() -> TextSanitizer.sanitizeText(aboutMe)).thenReturn(aboutMe);
            when(profileService.updateAboutMeText(username, aboutMe)).thenReturn(expectedResponse);


        // When
        ResponseEntity<String> response = profileController.updateAboutMeText(aboutMe, principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(profileService).updateAboutMeText(username, aboutMe);
        }
    }

    @Test
    @DisplayName("Should return bad request if 'About Me' text is too long")
    void updateAboutMeText_ShouldReturnBadRequest_IfTextTooLong() {
        // Given
        String aboutMe = "a".repeat(1001); // 1001 characters

        // When
        ResponseEntity<String> response = profileController.updateAboutMeText(aboutMe, principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("text is too long (1000 characters maximum)");
    }

    @Test
    @DisplayName("Should return bad request if 'About Me' text is empty")
    void updateAboutMeText_ShouldReturnBadRequest_IfTextIsEmpty() {
        // Given
        String aboutMe = "";

        // When
        ResponseEntity<String> response = profileController.updateAboutMeText(aboutMe, principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("no text received");
    }

    @Test
    @DisplayName("Should delete account for logged-in user and return success message")
    void deleteAccount_ShouldReturnSuccessMessage() {
        // Given
        String username = "testUser";
        when(principal.getName()).thenReturn(username);
        String expectedResponse = "Account deleted successfully";
        when(profileService.deleteAccount(username)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = profileController.deleteAccount(username, principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(profileService).deleteAccount(username);
    }

    @Test
    @DisplayName("Should return unauthorized if user tries to delete another account")
    void deleteAccount_ShouldReturnUnauthorized_IfNotSameUser() {
        // Given
        String loggedInUsername = "testUser";
        String otherUsername = "anotherUser";
        when(principal.getName()).thenReturn(loggedInUsername);

        // When
        ResponseEntity<String> response = profileController.deleteAccount(otherUsername, principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("unauthorized");
    }

    @Test
    @DisplayName("Should change user password and return success message")
    void changeUserPassword_ShouldReturnSuccessMessage() {
        // Given
        String username = "testUser";
        String newPassword = "newPassword123";
        String expectedResponse = "Password changed successfully";
        when(customUserDetailsService.changeUserPassword(username, newPassword)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = profileController.changeUserPassword(username, newPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(customUserDetailsService).changeUserPassword(username, newPassword);
    }

    @Test
    @DisplayName("Should update secret question and answer for logged-in user")
    void changeSecretQuestion_ShouldUpdateSecretQuestion() {
        // Given
        String username = "testUser";
        String secretQuestion = "What is your favorite color?";
        String secretAnswer = "Blue";
        JuxBarUser user = new JuxBarUser();
        when(principal.getName()).thenReturn(username);
        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);

        // When
        ResponseEntity<String> response = profileController.changeSecretQuestion(secretQuestion, secretAnswer, principal);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("secret question updated");
        verify(juxBarUserService).saveJuxBarUser(user);
    }

    @Test
    @DisplayName("Should create new account and return success message")
    void createAccount_ShouldReturnSuccessMessage_WhenAccountCreated() {
        // Given
        String username = "testUser";
        String secretQuestion = "What is your favorite color?";
        String secretAnswer = "Blue";
        String password = "password123";
        JuxBarUser savedUser = new JuxBarUser();
        savedUser.setUsername(username);
        savedUser.setSecretQuestion(secretQuestion);
        savedUser.setSecretAnswer(secretAnswer);
        savedUser.setPassword(password);
        savedUser.setActive(true);

        doNothing().when(juxBarUserService).saveJuxBarUser(username, secretQuestion, secretAnswer, password);
        when(juxBarUserService.getJuxBarUserByUsername(username))
                .thenReturn(null)
                .thenReturn(savedUser);
        // When
        ResponseEntity<String> response = profileController.createAccount(username, secretQuestion, secretAnswer, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("utilisateur créé avec le nom : " + username + " !!");
        verify(juxBarUserService).saveJuxBarUser(username, secretQuestion, secretAnswer, password);
        verify(juxBarUserService, times(2)).getJuxBarUserByUsername(username);
    }

    @Test
    @DisplayName("Should return error message when user already exists")
    void createAccount_ShouldReturnErrorMessage_WhenUserAlreadyExists() {
        // Given
        String username = "existingUser";
        String secretQuestion = "What is your favorite color?";
        String secretAnswer = "Blue";
        String password = "password123";
        JuxBarUser existingUser = new JuxBarUser();
        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(existingUser);

        // When
        ResponseEntity<String> response = profileController.createAccount(username, secretQuestion, secretAnswer, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("cet utilisateur existe déjà !!");
    }
}
