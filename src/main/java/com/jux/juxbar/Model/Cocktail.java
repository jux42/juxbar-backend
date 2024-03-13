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
        @Column(name = "id")
        private Integer id;

        @Column(name = "idDrink")
        private String idDrink;

        @Column(name = "strDrink")
        private String strDrink;

        @Column(name = "strDrinkThumb")
        private String strDrinkThumb;

        @Column(name = "strInstructions")
        private String strInstructions;

        @Column(name = "strIngredient1")
        private String strIngredient1;

        @Column(name = "strIngredient2")
        private String strIngredient2;

        @Column(name = "strIngredient3")
        private String strIngredient3;

        @Column(name = "strIngredient4")
        private String strIngredient4;

        @Column(name = "strIngredient5")
        private String strIngredient5;

        @Column(name = "strIngredient6")
        private String strIngredient6;

        @Column(name = "strIngredient7")
        private String strIngredient7;

        @Column(name = "imageData")
        private byte[] imageData;

    }

