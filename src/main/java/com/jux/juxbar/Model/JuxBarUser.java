package com.jux.juxbar.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CollectionType;

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
    private Integer id;

    private String username;


    private String password;

    private String role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PersonalCocktail> personalCocktails;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Cocktail> favourite_cocktails;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SoftDrink> favourite_softdrinks;


    public String addFav(Cocktail cocktail){
        this.getFavourite_cocktails().add(cocktail);
        return ""+cocktail.getStrDrink()+" added as fav for "+ this.getUsername();
    }

    public String addFav(SoftDrink soft){
        this.getFavourite_softdrinks().add(soft);
        return ""+soft.getStrDrink()+" added as fav for "+ this.getUsername();
    }

    public String rmFav(Cocktail cocktail){
        this.getFavourite_cocktails().remove(cocktail);
        return ""+cocktail.getStrDrink()+" removed as fav for "+ this.getUsername();
    }

    public String rmFav(SoftDrink soft){
        this.getFavourite_softdrinks().remove(soft);
        return ""+soft.getStrDrink()+" removed as fav for "+ this.getUsername();
    }



//    public List<Integer> getFavouriteCocktails(){
//        if(favourite_cocktails == null) return null;
//        return Arrays.stream(favourite_cocktails.split(","))
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
//    }
//
//    public List<Integer> getFavouriteSoftDrinks(){
//        if(favourite_softdrinks == null) return null;
//        return Arrays.stream(favourite_softdrinks.split(","))
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
//    }

//    public void setFavouriteCocktails(List<Integer> favouritesList){
//        this.favourite_cocktails = favouritesList.stream()
//                .map(Object::toString)
//                .collect(Collectors.joining(","));
//    }
//
//    public void setFavouriteSoftDrinks(List<Integer> favouritesList){
//        this.favourite_softdrinks = favouritesList.stream()
//                .map(Object::toString)
//                .collect(Collectors.joining(","));
//    }


}
