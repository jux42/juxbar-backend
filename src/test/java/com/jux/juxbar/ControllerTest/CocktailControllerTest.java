package com.jux.juxbar.ControllerTest;

//package com.jux.juxbar.ControllerTest;
//
//import com.jux.juxbar.Controller.CocktailController;
//import com.jux.juxbar.Model.Cocktail;
//import com.jux.juxbar.Service.CocktailService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import java.util.Arrays;
//import java.util.Optional;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Disabled
//@ExtendWith(MockitoExtension.class)
public class CocktailControllerTest {

}
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private CocktailService cocktailService;
//
//    @InjectMocks
//    private CocktailController cocktailController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(cocktailController).build();
//    }
//
//    @Test
//    public void testGetCocktail_CallsService() throws Exception {
//        // Given
//        Cocktail cocktail = new Cocktail();
//        cocktail.setId(1);
//        cocktail.setIdDrink("12345");
//
//        when(cocktailService.getCocktail(anyInt())).thenReturn(Optional.of(cocktail));
//
//        // When & Then
//        mockMvc.perform(get("/cocktails")
//                        .param("page", "0")
//                        .param("limit", "10")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(cocktailService, times(1)).getCocktail(1);
//    }
//
//    @Test
//    public void testGetCocktail_NotFound() throws Exception {
//        // Given
//        when(cocktailService.getCocktail(anyInt())).thenReturn(Optional.empty());
//
//        // When & Then
//        mockMvc.perform(get("/cocktail/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("null"));
//
//        verify(cocktailService, times(1)).getCocktail(1);
//    }
//
//    @Test
//    public void testGetCocktails_CallsService() throws Exception {
//        // Given
//        Cocktail cocktail1 = new Cocktail();
//        cocktail1.setId(1);
//        cocktail1.setIdDrink("12345");
//        cocktail1.setStrDrink("Mojito");
//
//        Cocktail cocktail2 = new Cocktail();
//        cocktail2.setId(2);
//        cocktail2.setIdDrink("67890");
//        cocktail2.setStrDrink("Martini");
//
//        Iterable<Cocktail> cocktails = Arrays.asList(cocktail1, cocktail2);
//
//        when(cocktailService.getAllCocktails()).thenReturn(cocktails);
//
//        // When
//        mockMvc.perform(get("/cocktails"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[{'id':1,'idDrink':'12345','strDrink':'Mojito'},{'id':2,'idDrink':'67890','strDrink':'Martini'}]"));
//
//        // Then
//        verify(cocktailService).getAllCocktails();
//    }
//
//    @Test
//    @DisplayName("Controller calls service and return Response with image")
//    public void testGetImage_ReturnsImage() throws Exception {
//
//        // Given
//        byte[] compressedFakeImage = "compressedImageData".getBytes();
//
//        when(cocktailService.getImage(anyInt())).thenReturn(ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(compressedFakeImage));
//
//        // When
//        mockMvc.perform(get("/cocktail/1/image"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
//                .andExpect(content().bytes(compressedFakeImage));
//
//        // Then
//        verify(cocktailService, times(1)).getImage(1);
//    }
//
//
//
//
//
//    }
//
//
