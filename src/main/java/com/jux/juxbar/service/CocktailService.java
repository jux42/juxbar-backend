package com.jux.juxbar.service;

import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.interfaces.DrinkServiceInterface;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.repository.CocktailImageRepository;
import com.jux.juxbar.repository.CocktailRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CocktailService extends Thread implements DrinkServiceInterface<Cocktail> {

    private final CocktailRepository cocktailRepository;
    private final ImageCompressor imageCompressor;
    private final CocktailImageRepository cocktailImageRepository;


    @PostConstruct
    @Cacheable("cocktails")
    public void initCocktailCache(){
        log.info("initializing cocktail cache...");
        cocktailRepository.findAll();
        log.info("cache initialized !");
    }

    @PostConstruct
    @Cacheable("cocktailImages")
    public void initImagesCache(){
        log.info("initializing cocktail images cache...");
        cocktailImageRepository.findAll();
        log.info("image cache initialized !");
    }


    @Override
    public Optional<Cocktail> getDrinkByIdDrink(String idDrink) {
        return cocktailRepository.findByIdDrink(idDrink);
    }

    @Override
    public void saveDrink(Cocktail cocktail) {
        cocktailRepository.save(cocktail);
    }

    @Override
    @CachePut(value = "cocktail", key = "#id")
    public Optional<Cocktail> getDrink(int id) {
        return cocktailRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "cocktail", key = "#id")
    public Optional<Cocktail> getDrinkNoCache(int id) {
        return cocktailRepository.findById(id);
    }

    @Override
    @CachePut("cocktails")
    public Iterable<Cocktail> getAllDrinks() {
        return cocktailRepository.findAll();
    }

    @Override
    @CacheEvict("cocktails")
    public Iterable<Cocktail> getAllDrinksNoCache() {
        return cocktailRepository.findAll();
    }

    @Override
    public Page<Cocktail> getDrinks(Pageable pageable) {
        return cocktailRepository.findAll(pageable);
    }

    @Cacheable(value = "image", key = "#id")
    public byte[] getImage(int id) {
        return this.getDrink(id)
                .map(cocktail -> {
                    byte[] compressedImage;
                    try {
                      return compressedImage = imageCompressor.compress(cocktail.getImageData().getImage(), "jpg", 0.4);
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }

                })
                .orElseGet(() -> null);
    }



    @Cacheable(value = "preview", key = "#id")
    public byte[] getPreview(int id) {
        return this.getDrink(id)
                .map(cocktail -> {
                    byte[] compressedImage;
                    try {
                     return compressedImage = imageCompressor.compress(cocktail.getImageData().getImage(), "jpg", 0.2);
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }

                })
                .orElseGet(() -> null);
    }



    }

