package com.jux.juxbar.Model;

import lombok.Data;

import java.util.List;

@Data
public class CocktailResponse {
    private List<Cocktail> drinks;
    private Cocktail drink;

}