package com.jux.juxbar.model;

import lombok.Data;

import java.util.List;

@Data
public class CocktailResponse {
    private List<Cocktail> drinks;
    private Cocktail drink;

}