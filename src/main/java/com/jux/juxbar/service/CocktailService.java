package com.jux.juxbar.service;

import com.jux.juxbar.interfaces.DrinkApiInteractorInterface;
import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.interfaces.DrinkServiceInterface;
import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.CocktailResponse;
import com.jux.juxbar.repository.CocktailRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
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


    @PostConstruct
    private void initCache(){
        log.info("initializing cocktail cache...");
        cocktailRepository.findAll();
        log.info("cache initialized !");
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
    @Cacheable(value = "cocktail", key = "#id")
    public Optional<Cocktail> getDrink(int id) {
        return cocktailRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "cocktail", key = "#id")
    public Optional<Cocktail> getDrinkNoCache(int id) {
        return cocktailRepository.findById(id);
    }

    @Override
    @Cacheable("cocktails")
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

    @Override
    public Iterable<Cocktail> getDrinks() {
        return null;
    }



    @Cacheable("image")
    public ResponseEntity<byte[]> getImage(int id) {
        return this.getDrink(id)
                .map(cocktail -> {
                    byte[] compressedImage;
                    try {
                        compressedImage = imageCompressor.compress(cocktail.getImageData(), "jpg");
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(compressedImage);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    public ResponseEntity<byte[]> getImageNoCache(int id) {
        return this.getDrinkNoCache(id)
                .map(cocktail -> {
                    byte[] compressedImage;
                    try {
                        compressedImage = imageCompressor.compress(cocktail.getImageData(), "jpg");
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(compressedImage);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<byte[]> getPreview(int id) {
        return this.getDrinkNoCache(id)
                .map(cocktail -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(cocktail.getPreview()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    }

