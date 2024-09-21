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
        personalCocktail.setStrIngredient1(Jsoup.clean(personalCocktail.getStrIngredient1(),Safelist.simpleText()));
        personalCocktail.setStrIngredient2(Jsoup.clean(personalCocktail.getStrIngredient2(),Safelist.simpleText()));
        personalCocktail.setStrIngredient3(Jsoup.clean(personalCocktail.getStrIngredient3(),Safelist.simpleText()));
        personalCocktail.setStrIngredient4(Jsoup.clean(personalCocktail.getStrIngredient4(),Safelist.simpleText()));
        personalCocktail.setStrIngredient5(Jsoup.clean(personalCocktail.getStrIngredient5(),Safelist.simpleText()));
        personalCocktail.setStrIngredient6(Jsoup.clean(personalCocktail.getStrIngredient6(),Safelist.simpleText()));
        personalCocktail.setStrIngredient7(Jsoup.clean(personalCocktail.getStrIngredient7(),Safelist.simpleText()));

        return personalCocktail;
    }
}
