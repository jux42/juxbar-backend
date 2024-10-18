package com.jux.juxbar.model.dto;


import com.jux.juxbar.model.Drink;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CocktailDto extends Drink {

    private Integer id;

    protected String idDrink;

    protected String strDrink;

    protected String strDrinkThumb;

    protected String strInstructions;

    protected String strIngredient1;
    protected String strIngredient2;
    protected String strIngredient3;
    protected String strIngredient4;
    protected String strIngredient5;
    protected String strIngredient6;
    protected String strIngredient7;

}
