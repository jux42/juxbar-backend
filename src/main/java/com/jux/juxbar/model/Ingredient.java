package com.jux.juxbar.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ingredient")
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String strIngredient;

    private String idIngredient;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String strDescription;

    @OneToOne
    @JoinColumn(name = "image_data_id")
    private IngredientImage imageData;

}
