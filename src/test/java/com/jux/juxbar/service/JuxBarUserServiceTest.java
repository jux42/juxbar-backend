package com.jux.juxbar.service;

import com.jux.juxbar.configuration.CustomUserDetailsService;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.repository.JuxBarUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JuxBarUserServiceTest {

    @Mock
    private JuxBarUserRepository juxBarUserRepository;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private JuxBarUserService juxBarUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return JuxBarUser by username")
    void getJuxBarUserByUsername_ShouldReturnUser() {
        // Given
        String username = "testUser";
        JuxBarUser user = new JuxBarUser();
        when(juxBarUserRepository.findByUsername(username)).thenReturn(user);

        // When
        JuxBarUser result = juxBarUserService.getJuxBarUserByUsername(username);

        // Then
        assertThat(result).isEqualTo(user);
        verify(juxBarUserRepository).findByUsername(username);
    }

    @Test
    @DisplayName("Should return all JuxBarUsers")
    void getAllJuxBarUsers_ShouldReturnAllUsers() {
        // Given
        List<JuxBarUser> userList = new ArrayList<>();
        when(juxBarUserRepository.findAll()).thenReturn(userList);

        // When
        List<JuxBarUser> result = juxBarUserService.getAllJuxBarUsers();

        // Then
        assertThat(result).isEqualTo(userList);
        verify(juxBarUserRepository).findAll();
    }

    @Test
    @DisplayName("Should save JuxBarUser object")
    void saveJuxBarUser_ShouldSaveUser() {
        // Given
        JuxBarUser user = new JuxBarUser();

        // When
        juxBarUserService.saveJuxBarUser(user);

        // Then
        verify(juxBarUserRepository).save(user);
    }

    @Test
    @DisplayName("Should create JuxBarUser with username, secretQuestion, secretAnswer, and password")
    void saveJuxBarUser_ShouldCreateUserWithDetails() {
        // Given
        String username = "testUser";
        String secretQuestion = "What is your favorite color?";
        String secretAnswer = "Blue";
        String password = "password123";

        // When
        juxBarUserService.saveJuxBarUser(username, secretQuestion, secretAnswer, password);

        // Then
        verify(customUserDetailsService).createUser(username, secretQuestion, secretAnswer, password);
    }

    @Test
    @DisplayName("Should create JuxBarUser with username and password")
    void saveJuxBarUser_ShouldCreateUserWithUsernameAndPassword() {
        // Given
        String username = "testUser";
        String password = "password123";

        // When
        juxBarUserService.saveJuxBarUser(username, password);

        // Then
        verify(customUserDetailsService).createUser(username, password);
    }

    @Test
    @DisplayName("Should reactivate user when inactive")
    void reactivateUser_ShouldReactivateUser() {
        // Given
        String username = "testUser";
        JuxBarUser user = new JuxBarUser();
        user.setActive(false);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(user);

        // When
        String result = juxBarUserService.reactivateUser(username);

        // Then
        assertThat(result).isEqualTo("user reactivated successfully");
        verify(juxBarUserRepository).save(user);
    }

    @Test
    @DisplayName("Should return message when user already activated")
    void reactivateUser_ShouldReturnMessageWhenUserAlreadyActivated() {
        // Given
        String username = "testUser";
        JuxBarUser user = new JuxBarUser();
        user.setActive(true);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(user);

        // When
        String result = juxBarUserService.reactivateUser(username);

        // Then
        assertThat(result).isEqualTo("user already activated");
        verify(juxBarUserRepository, never()).save(user);
    }

    @Test
    @DisplayName("Should return message when user not found during reactivation")
    void reactivateUser_ShouldReturnMessageWhenUserNotFound() {
        // Given
        String username = "testUser";
        when(juxBarUserRepository.findByUsername(username)).thenReturn(null);

        // When
        String result = juxBarUserService.reactivateUser(username);

        // Then
        assertThat(result).isEqualTo("user not found");
        verify(juxBarUserRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should disable user when active")
    void disableUser_ShouldDisableUser() {
        // Given
        String username = "testUser";
        JuxBarUser user = new JuxBarUser();
        user.setActive(true);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(user);

        // When
        String result = juxBarUserService.disableUser(username);

        // Then
        assertThat(result).isEqualTo("user disabled successfully");
        verify(juxBarUserRepository).save(user);
    }

    @Test
    @DisplayName("Should return message when user already inactive")
    void disableUser_ShouldReturnMessageWhenUserAlreadyInactive() {
        // Given
        String username = "testUser";
        JuxBarUser user = new JuxBarUser();
        user.setActive(false);
        when(juxBarUserRepository.findByUsername(username)).thenReturn(user);

        // When
        String result = juxBarUserService.disableUser(username);

        // Then
        assertThat(result).isEqualTo("user already inactive");
        verify(juxBarUserRepository, never()).save(user);
    }

    @Test
    @DisplayName("Should return message when user not found during disable")
    void disableUser_ShouldReturnMessageWhenUserNotFound() {
        // Given
        String username = "testUser";
        when(juxBarUserRepository.findByUsername(username)).thenReturn(null);

        // When
        String result = juxBarUserService.disableUser(username);

        // Then
        assertThat(result).isEqualTo("user not found");
        verify(juxBarUserRepository, never()).save(any());
    }

}
