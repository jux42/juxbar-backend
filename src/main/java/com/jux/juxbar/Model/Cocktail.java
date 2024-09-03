package com.jux.juxbar.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
    public class Cocktail extends Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    }

