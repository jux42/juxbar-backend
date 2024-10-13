package com.jux.juxbar.controller;

import com.jux.juxbar.configuration.AppName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AppNameControllerTest {

    @Mock
    private AppName appName;

    @InjectMocks
    private AppNameController appNameController;

    private static final Logger log = LoggerFactory.getLogger(AppNameController.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return the app name when it's not null")
    void getAppName_ShouldReturnAppName_WhenNotNull() {
        // Given
        String expectedAppName = "JuxBar";
        when(appName.getName()).thenReturn(expectedAppName);

        // When
        String result = appNameController.getAppName();

        // Then
        assertThat(result).isEqualTo(expectedAppName);
    }

    @Test
    @DisplayName("Should return '???' when app name is null")
    void getAppName_ShouldReturnUnknown_WhenAppNameIsNull() {
        // Given
        when(appName.getName()).thenReturn(null);

        // When
        String result = appNameController.getAppName();

        // Then
        assertThat(result).isEqualTo("???");
    }
}
