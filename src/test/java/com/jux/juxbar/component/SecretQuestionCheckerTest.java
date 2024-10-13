package com.jux.juxbar.component;

import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.repository.JuxBarUserRepository;
import com.jux.juxbar.service.JuxBarUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SecretQuestionCheckerTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JuxBarUserRepository juxBarUserRepository;

    @Mock
    private JuxBarUserService juxBarUserService;

    @InjectMocks
    private SecretQuestionChecker secretQuestionChecker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should change password when secret question and answer are correct")
    void checkSecretAnswer_ShouldChangePassword_WhenCorrectAnswer() {
        // Given
        String username = "testUser";
        String secretQuestion = "What is your favorite color?";
        String secretAnswer = "Blue";
        String password = "newPassword123";

        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(username);
        juxBarUser.setSecretQuestion(secretQuestion);
        juxBarUser.setSecretAnswer("$2a$10$hashedSecretAnswer");

        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(juxBarUser);
        when(bCryptPasswordEncoder.matches(secretAnswer, juxBarUser.getSecretAnswer())).thenReturn(true);
        when(bCryptPasswordEncoder.encode(password)).thenReturn("$2a$10$hashedPassword");

        // When
        String result = secretQuestionChecker.checkSecretAnswer(username, secretQuestion, secretAnswer, password);

        // Then
        assertThat(result).isEqualTo("Password successfully changed");
        verify(juxBarUserRepository).save(juxBarUser);
        verify(bCryptPasswordEncoder).encode(password);
    }

    @Test
    @DisplayName("Should return error message when secret question or answer is incorrect")
    void checkSecretAnswer_ShouldReturnError_WhenIncorrectAnswer() {
        // Given
        String username = "testUser";
        String secretQuestion = "What is your favorite color?";
        String secretAnswer = "Red"; // Incorrect answer
        String password = "newPassword123";

        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(username);
        juxBarUser.setSecretQuestion(secretQuestion);
        juxBarUser.setSecretAnswer("$2a$10$hashedSecretAnswer");

        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(juxBarUser);
        when(bCryptPasswordEncoder.matches(secretAnswer, juxBarUser.getSecretAnswer())).thenReturn(false);

        // When
        String result = secretQuestionChecker.checkSecretAnswer(username, secretQuestion, secretAnswer, password);

        // Then
        assertThat(result).isEqualTo("You were not allowed to change your password, please contact your administrator");
        verify(juxBarUserRepository, never()).save(juxBarUser);
        verify(bCryptPasswordEncoder, never()).encode(password);
    }
}
