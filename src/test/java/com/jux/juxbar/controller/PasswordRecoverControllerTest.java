package com.jux.juxbar.controller;

import com.jux.juxbar.component.SecretQuestionChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PasswordRecoverControllerTest {

    @Mock
    private SecretQuestionChecker secretQuestionChecker;

    @InjectMocks
    private passwordRecoverController passwordRecoverController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return success message when secret answer is correct")
    void recoverPassword_ShouldReturnSuccess_WhenSecretAnswerIsCorrect() {
        // Given
        String username = "testUser";
        String secretQuestion = "What is your favorite color?";
        String secretAnswer = "Blue";
        String newPassword = "newPassword123";
        String expectedResponse = "Password successfully changed";

        when(secretQuestionChecker.checkSecretAnswer(username, secretQuestion, secretAnswer, newPassword))
                .thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = passwordRecoverController.recoverPassword(username, secretQuestion, secretAnswer, newPassword);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(secretQuestionChecker).checkSecretAnswer(username, secretQuestion, secretAnswer, newPassword);
    }

    @Test
    @DisplayName("Should return error message when secret answer is incorrect")
    void recoverPassword_ShouldReturnError_WhenSecretAnswerIsIncorrect() {
        // Given
        String username = "testUser";
        String secretQuestion = "What is your favorite color?";
        String secretAnswer = "Red"; // Incorrect answer
        String newPassword = "newPassword123";
        String expectedResponse = "Secret answer is incorrect";

        when(secretQuestionChecker.checkSecretAnswer(username, secretQuestion, secretAnswer, newPassword))
                .thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = passwordRecoverController.recoverPassword(username, secretQuestion, secretAnswer, newPassword);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(secretQuestionChecker).checkSecretAnswer(username, secretQuestion, secretAnswer, newPassword);
    }
}
