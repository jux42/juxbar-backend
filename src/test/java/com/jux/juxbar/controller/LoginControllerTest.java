package com.jux.juxbar.controller;

import com.jux.juxbar.component.ActiveUserChecker;
import com.jux.juxbar.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ActiveUserChecker activeUserChecker;

    @Mock
    private Principal principal;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return token when login is successful")
    void getToken_ShouldReturnTokenWhenLoginIsSuccessful() {
        // Given
        String username = "testUser";
        String password = "testPassword";
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(activeUserChecker.checkIfActive(username)).thenReturn(true);
        when(jwtService.generateToken(authentication)).thenReturn("mockedToken");

        // When
        ResponseEntity<String> response = loginController.getToken(username, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("mockedToken");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(activeUserChecker).checkIfActive(username);
        verify(jwtService).generateToken(authentication);
    }

    @Test
    @DisplayName("Should return forbidden when user is deactivated")
    void getToken_ShouldReturnForbiddenWhenUserIsDeactivated() {
        // Given
        String username = "testUser";
        String password = "testPassword";
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(activeUserChecker.checkIfActive(username)).thenReturn(false);

        // When
        ResponseEntity<String> response = loginController.getToken(username, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isEqualTo("This user has been deactivated. Please contact administrator");
        verify(activeUserChecker).checkIfActive(username);
    }

    @Test
    @DisplayName("Should return unauthorized when login fails")
    void getToken_ShouldReturnUnauthorizedWhenLoginFails() {
        // Given
        String username = "testUser";
        String password = "wrongPassword";
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(mock(AuthenticationException.class));
        // When
        ResponseEntity<String> response = loginController.getToken(username, password);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("Invalid username or password");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Should return username of the logged-in user")
    void getUsername_ShouldReturnUsername() {
        // Given
        when(principal.getName()).thenReturn("testUser");

        // When
        String username = loginController.getUsername(principal);

        // Then
        assertThat(username).isEqualTo("testUser");
        verify(principal, times(2)).getName();
    }

    @Test
    @DisplayName("Should return admin status when authenticated")
    void getAdmin_ShouldReturnAdminStatusWhenAuthenticated() {
        // Given
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

        // When
        ResponseEntity<String> response = loginController.getAdmin();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Admin is OK");
    }

    @Test
    @DisplayName("Should return unauthorized when not authenticated")
    void getAdmin_ShouldReturnUnauthorizedWhenNotAuthenticated() {
        // Given
        SecurityContextHolder.clearContext(); // Simuler aucun utilisateur authentifié

        // When
        ResponseEntity<String> response = loginController.getAdmin();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("You are not authenticated");
    }
}
