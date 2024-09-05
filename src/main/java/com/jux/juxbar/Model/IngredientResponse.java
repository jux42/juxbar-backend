package com.jux.juxbar.Model;

import lombok.Data;

import java.util.List;

@Data
public class IngredientResponse {
    private List<Ingredient> ingredients;
    private Ingredient ingredient;
}
