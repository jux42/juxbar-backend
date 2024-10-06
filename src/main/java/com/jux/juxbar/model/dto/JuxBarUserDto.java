package com.jux.juxbar.model.dto;

import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.PersonalCocktail;
import com.jux.juxbar.model.SoftDrink;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JuxBarUserDto {

    private String username;
    private boolean isActive;
    private String role;
    private byte[] profilePicture;
    private String aboutMeText;

    private List<PersonalCocktail> personalCocktails;
    private List<Cocktail> favourite_cocktails;
    private List<SoftDrink> favourite_softdrinks;
}


