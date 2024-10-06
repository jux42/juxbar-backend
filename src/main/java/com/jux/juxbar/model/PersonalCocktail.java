package com.jux.juxbar.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class PersonalCocktail extends Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ownerName;

    @Enumerated(EnumType.STRING)
    private State state;

    private byte[] localImage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_data_id")
    protected PersonalCocktailImage imageData ;

}
