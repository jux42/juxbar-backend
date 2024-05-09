package com.jux.juxbar.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@Table(name = "juxbaruser")
public class JuxBarUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "favourite_cocktails")
    private String favourite_cocktails;

    @Column(name = "favourite_softdrinks")
    private String favourite_softdrinks;

    public List<Integer> getFavouriteCocktails(){
        return Arrays.stream(favourite_cocktails.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public List<Integer> getFavouriteSoftDrinks(){
        return Arrays.stream(favourite_softdrinks.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public void setFavouriteCocktails(List<Integer> favouritesList){
        this.favourite_cocktails = favouritesList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    public void setFavouriteSoftDrinks(List<Integer> favouritesList){
        this.favourite_softdrinks = favouritesList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

}
