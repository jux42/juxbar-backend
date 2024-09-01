package com.jux.juxbar.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
    public class Cocktail extends Drink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    public String getStrIngredient1(){
//        return ingredient1.getStrIngredient();
//    }
//    public String getStrIngredient2(){
//        return ingredient2.getStrIngredient();
//    }
//    public String getStrIngredient3(){
//        return ingredient3.getStrIngredient();
//    }
//    public String getStrIngredient4(){
//        return ingredient4.getStrIngredient();
//    }
//    public String getStrIngredient5(){
//        return ingredient5.getStrIngredient();
//    }
//    public String getStrIngredient6(){
//        return ingredient6.getStrIngredient();
//    }
//    public String getStrIngredient7(){
//        return ingredient7.getStrIngredient();
//    }
    }

