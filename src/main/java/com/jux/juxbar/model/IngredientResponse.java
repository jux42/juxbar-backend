package com.jux.juxbar.model;

import lombok.Data;

import java.util.List;

@Data
public class IngredientResponse {
    private List<Ingredient> ingredients;
    private Ingredient ingredient;
}
