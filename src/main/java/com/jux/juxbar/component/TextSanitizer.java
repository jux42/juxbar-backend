package com.jux.juxbar.component;

import com.jux.juxbar.model.PersonalCocktail;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class TextSanitizer {

    public static String sanitizeText(String text) {
        return Jsoup.clean(text, Safelist.simpleText());
    }

    public static PersonalCocktail sanitizeCocktailText(PersonalCocktail personalCocktail) {


        personalCocktail.setStrIngredient1( personalCocktail.getStrIngredient1().isEmpty() ?
                "":
                Jsoup.clean(personalCocktail.getStrIngredient1(),Safelist.simpleText()));

        personalCocktail.setStrIngredient1( personalCocktail.getStrIngredient1().isEmpty() ?
                "":
                Jsoup.clean(personalCocktail.getStrIngredient2(),Safelist.simpleText()));

        personalCocktail.setStrIngredient2( personalCocktail.getStrIngredient2().isEmpty() ?
                "":
                Jsoup.clean(personalCocktail.getStrIngredient3(),Safelist.simpleText()));

        personalCocktail.setStrIngredient3( personalCocktail.getStrIngredient3().isEmpty() ?
                "":
                Jsoup.clean(personalCocktail.getStrIngredient1(),Safelist.simpleText()));

        personalCocktail.setStrIngredient4( personalCocktail.getStrIngredient4().isEmpty() ?
                "":
                Jsoup.clean(personalCocktail.getStrIngredient4(),Safelist.simpleText()));

        personalCocktail.setStrIngredient5( personalCocktail.getStrIngredient5().isEmpty() ?
                "":
                Jsoup.clean(personalCocktail.getStrIngredient5(),Safelist.simpleText()));

        personalCocktail.setStrIngredient6( personalCocktail.getStrIngredient6().isEmpty() ?
                "":
                Jsoup.clean(personalCocktail.getStrIngredient6(),Safelist.simpleText()));

        return personalCocktail;
    }
}
