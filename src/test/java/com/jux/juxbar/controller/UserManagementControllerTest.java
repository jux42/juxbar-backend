package com.jux.juxbar.controller;

import com.jux.juxbar.configuration.CustomUserDetailsService;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.service.JuxBarUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserManagementControllerTest {

    @Mock
    private JuxBarUserService juxBarUserService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private UserManagementController userManagementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all users with masked passwords")
    void getUsers_ShouldReturnUsersWithMaskedPasswords() {
        // Given
        JuxBarUser user1 = new JuxBarUser();
        user1.setUsername("user1");
        user1.setPassword("password1");

        JuxBarUser user2 = new JuxBarUser();
        user2.setUsername("user2");
        user2.setPassword("password2");

        List<JuxBarUser> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(juxBarUserService.getAllJuxBarUsers()).thenReturn(users);

        // When
        ResponseEntity<Iterable<JuxBarUser>> response = userManagementController.getUsers();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains(user1, user2);
        assertThat(user1.getPassword()).isEqualTo("******");
        assertThat(user2.getPassword()).isEqualTo("******");
        verify(juxBarUserService).getAllJuxBarUsers();
    }

    @Test
    @DisplayName("Should create new user and return success message")
    void createUser_ShouldReturnSuccessMessage_WhenUserCreated() {
        // Given
        String username = "testUser";
        String password = "password123";
        JuxBarUser savedUser = new JuxBarUser();
        savedUser.setUsername(username);
        savedUser.setPassword(password);
        savedUser.setActive(true);

        doNothing().when(juxBarUserService).saveJuxBarUser(username, password);
        when(juxBarUserService.getJuxBarUserByUsername(username))
                .thenReturn(null)
                .thenReturn(savedUser);
        // When
        ResponseEntity<String> response = userManagementController.createUser(username, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("utilisateur créé avec le nom : " + username + " !!");
        verify(juxBarUserService).saveJuxBarUser(username, password);
        verify(juxBarUserService, times(2)).getJuxBarUserByUsername(username);

    }

    @Test
    @DisplayName("Should return error message when user already exists")
    void createUser_ShouldReturnErrorMessage_WhenUserAlreadyExists() {
        // Given
        String username = "existingUser";
        String password = "password123";
        JuxBarUser existingUser = new JuxBarUser();
        existingUser.setUsername(username);

        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(existingUser);

        // When
        ResponseEntity<String> response = userManagementController.createUser(username, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("cet utilisateur existe déjà !!");
        verify(juxBarUserService, never()).saveJuxBarUser(anyString(), anyString());
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
        ResponseEntity<String> response = userManagementController.changeUserPassword(username, newPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(customUserDetailsService).changeUserPassword(username, newPassword);
    }

    @Test
    @DisplayName("Should reactivate user and return success message")
    void reactivateUser_ShouldReturnSuccessMessage() {
        // Given
        String username = "testUser";
        String expectedResponse = "User reactivated";

        when(juxBarUserService.reactivateUser(username)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = userManagementController.reactivateUser(username);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(juxBarUserService).reactivateUser(username);
    }

    @Test
    @DisplayName("Should disable user and return success message")
    void inactivateUser_ShouldReturnSuccessMessage() {
        // Given
        String username = "testUser";
        String expectedResponse = "User disabled";

        when(juxBarUserService.disableUser(username)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = userManagementController.inactivateUser(username);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(juxBarUserService).disableUser(username);
    }
}
