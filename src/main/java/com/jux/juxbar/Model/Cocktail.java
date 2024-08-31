package com.jux.juxbar.Model;

import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;


@Data
    @Entity
    @Table(name = "cocktail")
    public class Cocktail implements Serializable {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String idDrink;

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

        private String strIngredient7;

        @Lob
        @Column(columnDefinition = "LONGBLOB")
        private byte[] imageData;

        @Lob
        @Column(columnDefinition = "LONGBLOB")
        private byte[] preview;



    }

