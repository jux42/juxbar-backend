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
    private Integer id;

    private String strIngredient;

    private String idIngredient;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String  strDescription;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] smallImageData;

}
