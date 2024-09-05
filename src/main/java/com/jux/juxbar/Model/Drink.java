package com.jux.juxbar.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Drink implements Serializable {


    protected String idDrink;

    protected String strDrink;

    protected String strDrinkThumb;

    @Lob
    @Column(columnDefinition = "TEXT")
    protected String strInstructions;


    protected String strIngredient1;
    protected String strIngredient2;
    protected String strIngredient3;
    protected String strIngredient4;
    protected String strIngredient5;
    protected String strIngredient6;
    protected String strIngredient7;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    protected byte[] imageData;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    protected byte[] preview;


}
