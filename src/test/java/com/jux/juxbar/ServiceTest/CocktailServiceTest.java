package com.jux.juxbar.ServiceTest;


import com.jux.juxbar.Model.Cocktail;
import com.jux.juxbar.Repository.CocktailRepository;
import com.jux.juxbar.Service.CocktailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CocktailServiceTest {

    MockMvc mockMvc;

    @Mock
    private CocktailRepository cocktailRepository;

    @InjectMocks
    private CocktailService cocktailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cocktailService).build();

    }


    @Test
    @DisplayName("Cocktail Service should call Repository and return List of cocktails")
    public void testGetAllCocktail_CallsRepository(){
        // Given
        List<Cocktail> cocktails = Arrays.asList(new Cocktail(), new Cocktail(), new Cocktail());
        when(cocktailRepository.findAll()).thenReturn(cocktails);

        // When

        Iterable<Cocktail> actualCocktails = cocktailService.getAllCocktails();

        // Then

        assertEquals(cocktails, actualCocktails);
        verify(cocktailRepository).findAll();


    }
}
