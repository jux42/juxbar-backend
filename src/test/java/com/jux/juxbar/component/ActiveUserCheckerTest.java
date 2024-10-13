package com.jux.juxbar.component;

import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.service.JuxBarUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ActiveUserCheckerTest {

    @Mock
    private JuxBarUserService juxBarUserService;

    @InjectMocks
    private ActiveUserChecker activeUserChecker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return true when user is active")
    void checkIfActive_ShouldReturnTrue_WhenUserIsActive() {
        // Given
        String username = "activeUser";
        JuxBarUser user = new JuxBarUser();
        user.setUsername(username);
        user.setActive(true);

        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);

        // When
        boolean isActive = activeUserChecker.checkIfActive(username);

        // Then
        assertThat(isActive).isTrue();
        verify(juxBarUserService).getJuxBarUserByUsername(username);
    }

    @Test
    @DisplayName("Should return false when user is not active")
    void checkIfActive_ShouldReturnFalse_WhenUserIsNotActive() {
        // Given
        String username = "inactiveUser";
        JuxBarUser user = new JuxBarUser();
        user.setUsername(username);
        user.setActive(false);

        when(juxBarUserService.getJuxBarUserByUsername(username)).thenReturn(user);

        // When
        boolean isActive = activeUserChecker.checkIfActive(username);

        // Then
        assertThat(isActive).isFalse();
        verify(juxBarUserService).getJuxBarUserByUsername(username);
    }
}
