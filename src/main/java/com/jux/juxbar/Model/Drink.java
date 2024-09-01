package com.jux.juxbar.Model;

import jakarta.persistence.*;
import lombok.Data;


@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Drink {



    protected String idDrink;

    protected String strDrink;

    protected String strDrinkThumb;

    @Lob
    @Column(columnDefinition = "TEXT")
    protected String strInstructions;

    @ManyToOne
    @JoinColumn(name = "ingredient_1_id")
    protected Ingredient ingredient1;
    @ManyToOne
    @JoinColumn(name = "ingredient_2_id")
    protected Ingredient ingredient2;
    @ManyToOne
    @JoinColumn(name = "ingredient_3_id")
    protected Ingredient ingredient3;
    @ManyToOne
    @JoinColumn(name = "ingredient_4_id")
    protected Ingredient ingredient4;
    @ManyToOne
    @JoinColumn(name = "ingredient_5_id")
    protected Ingredient ingredient5;
    @ManyToOne
    @JoinColumn(name = "ingredient_6_id")
    protected Ingredient ingredient6;
    @ManyToOne
    @JoinColumn(name = "ingredient_7_id")
    protected Ingredient ingredient7;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    protected byte[] imageData;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    protected byte[] preview;



}
