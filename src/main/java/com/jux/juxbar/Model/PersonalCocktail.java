package com.jux.juxbar.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "personal_cocktail")
public class PersonalCocktail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String strDrink;

    private String strDrinkThumb;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String strInstructions;

    private String strIngredient1;

    private String strIngredient2;

    private String strIngredient3;

    private String strIngredient4;

    private String strIngredient5;

    private String strIngredient6;

    @Column(name = "strIngredient7")
    private String strIngredient7;

    @Column(name = "imageData")
    private byte[] imageData;

    @Column(name = "preview")
    private byte[] preview;

    @Column(name = "ownerName")
    private String ownerName;


}
