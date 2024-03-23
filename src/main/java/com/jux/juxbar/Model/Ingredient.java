package com.jux.juxbar.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ingredient")
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "strIngredient")
    private String strIngredient;

    @Column(name = "idIngredient")
    private String idIngredient;

    @Column(name = "strDescription")
    private String  strDescription;

    @Column(name = "imageData")
    private byte[] imageData;

    @Column(name = "smallImageData")
    private byte[] smallImageData;

}
