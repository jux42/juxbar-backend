package com.jux.juxbar.service;

import com.jux.juxbar.component.ImageCompressor;
import com.jux.juxbar.interfaces.DrinkServiceInterface;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.repository.SoftDrinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class SoftDrinkService extends Thread implements DrinkServiceInterface<SoftDrink> {

    private final SoftDrinkRepository softDrinkRepository;
    private final ImageCompressor imageCompressor;

    @Override
    public Optional<SoftDrink> getDrinkByIdDrink(String idDrink) {
        return softDrinkRepository.findByIdDrink(idDrink);
    }

    @Override
    public void saveDrink(SoftDrink softDrink) {
        softDrinkRepository.save(softDrink);

    }

    @Override
    @Cacheable("softdrinks")
    public Optional<SoftDrink> getDrink(int id) {
        return softDrinkRepository.findById(id);
    }

    @Override
    @CacheEvict("softdrinks")
    public Optional<SoftDrink> getDrinkNoCache(int id) {
        return softDrinkRepository.findById(id);
    }

    @Override
    @Cacheable("softdrinks")
    public Iterable<SoftDrink> getAllDrinks() {
        return null;
    }

    @Override
    @CacheEvict("softdrinks")
    public Iterable<SoftDrink> getAllDrinksNoCache() {
        return null;
    }

    @Override
    public Iterable<SoftDrink> getDrinks(Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<SoftDrink> getDrinks() {
        return softDrinkRepository.findAll();
    }

    public ResponseEntity<byte[]> getImage(int id) {
        return this.getDrinkNoCache(id)
                .map(softDrink -> {

                    byte[] compressed = null;
                    try {
                        compressed = imageCompressor.compress(softDrink.getImageData().getImage(), "jpg");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(compressed);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<byte[]> getImageNoCache(int id) {
        return null;
    }

    public ResponseEntity<byte[]> getPreview(int id) {
        return this.getDrinkNoCache(id)
                .map(softDrink -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) //
                        .body(softDrink.getImageData().getPreview()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}