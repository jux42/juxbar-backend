package com.jux.juxbar.proxy;

import com.jux.juxbar.interfaces.DrinkServiceInterface;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.dto.CocktailDto;
import com.jux.juxbar.service.CocktailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@RequiredArgsConstructor
@Service
public class CocktailServiceProxy implements DrinkServiceInterface<CocktailDto> {

    private final CocktailService cocktailService;
    private List<CocktailDto> proxyCocktailDtos;
    private List<CocktailDto> proxyCocktailDtoPageable;
    private Map<Integer,byte[]> cocktailImagesMap = new HashMap<>();
    private HashMap<Integer,byte[]> cocktailPreviewMap = new HashMap<>();


    @Override
    public Optional<CocktailDto> getDrinkByIdDrink(String idDrink) {
        return Optional.empty();
    }

    @Override
    public void saveDrink(CocktailDto drink) {

    }


    @Override
    public Optional<CocktailDto> getDrink(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<CocktailDto> getDrinkNoCache(int id) {
        return Optional.empty();
    }

    @Override
    public Iterable<CocktailDto> getAllDrinks() {
        if (proxyCocktailDtos == null) {
            proxyCocktailDtos = new ArrayList<>();
            Iterable<Cocktail> proxyCocktails = cocktailService.getAllDrinks();
            proxyCocktails.forEach(cocktail -> {
                proxyCocktailDtos.add(
                        CocktailDto.builder()
                                .id(cocktail.getId())
                                .idDrink(cocktail.getIdDrink())
                                .strDrink(cocktail.getStrDrink())
                                .strInstructions(cocktail.getStrInstructions())
                                .strDrinkThumb(cocktail.getStrDrinkThumb())
                                .strIngredient1(cocktail.getStrIngredient1())
                                .strIngredient2(cocktail.getStrIngredient2())
                                .strIngredient3(cocktail.getStrIngredient3())
                                .strIngredient4(cocktail.getStrIngredient4())
                                .strIngredient5(cocktail.getStrIngredient5())
                                .strIngredient6(cocktail.getStrIngredient6())
                                .strIngredient7(cocktail.getStrIngredient7())
                                .build()
                );
            });
            return proxyCocktailDtos;
        }
        return proxyCocktailDtos;
    }

    @Override
    public Iterable<CocktailDto> getAllDrinksNoCache() {
        return null;
    }

    @Override
    public Iterable<CocktailDto> getDrinks(Pageable pageable) {
        if (proxyCocktailDtoPageable == null || !proxyCocktailDtoPageable.equals(pageable)) {
            proxyCocktailDtoPageable = new ArrayList<>();
            Page<Cocktail> cocktailPages = cocktailService.getDrinks(pageable);
            cocktailPages.forEach(cocktail -> {
                proxyCocktailDtoPageable.add(
                        CocktailDto.builder()
                                .id(cocktail.getId())
                                .idDrink(cocktail.getIdDrink())
                                .strDrink(cocktail.getStrDrink())
                                .strInstructions(cocktail.getStrInstructions())
                                .strDrinkThumb(cocktail.getStrDrinkThumb())
                                .strIngredient1(cocktail.getStrIngredient1())
                                .strIngredient2(cocktail.getStrIngredient2())
                                .strIngredient3(cocktail.getStrIngredient3())
                                .strIngredient4(cocktail.getStrIngredient4())
                                .strIngredient5(cocktail.getStrIngredient5())
                                .strIngredient6(cocktail.getStrIngredient6())
                                .strIngredient7(cocktail.getStrIngredient7())
                                .build()
                );

            });
            return proxyCocktailDtoPageable;

        }
        return proxyCocktailDtoPageable;

    }

    @Override
    public byte[] getImage(int id) {
        if(cocktailImagesMap != null && cocktailImagesMap.containsKey(id)){
            return cocktailImagesMap.get(id);
        }

        byte[] image = cocktailService.getImage(id);
        cocktailImagesMap.put(id,image);
        return  image;
    }

    @Override
    public byte[] getPreview(int id) {
        if(cocktailPreviewMap != null && cocktailPreviewMap.containsKey(id)){
            return cocktailPreviewMap.get(id);
        }

        byte[] preview = cocktailService.getPreview(id);
        cocktailPreviewMap.put(id,preview);
        return  preview;    }
}
