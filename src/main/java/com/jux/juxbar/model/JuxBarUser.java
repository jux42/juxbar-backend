package com.jux.juxbar.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@Table(name = "juxbaruser")
public class JuxBarUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private boolean isActive;
    private String password;
    private String role;
    private String secretQuestion;
    private String secretAnswer;
    private String email;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] profilePicture;

    private String aboutMeText;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PersonalCocktail> personalCocktails;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Cocktail> favourite_cocktails;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SoftDrink> favourite_softdrinks;


    public String addFav(Cocktail cocktail) {
        this.getFavourite_cocktails().add(cocktail);
        return cocktail.getStrDrink() + " added as fav for " + this.getUsername();
    }

    public String addFav(SoftDrink soft) {
        this.getFavourite_softdrinks().add(soft);
        return soft.getStrDrink() + " added as fav for " + this.getUsername();
    }

    public String rmFav(Cocktail cocktail) {
        this.getFavourite_cocktails().remove(cocktail);
        return cocktail.getStrDrink() + " removed as fav for " + this.getUsername();
    }

    public String rmFav(SoftDrink soft) {
        this.getFavourite_softdrinks().remove(soft);
        return soft.getStrDrink() + " removed as fav for " + this.getUsername();
    }


}
