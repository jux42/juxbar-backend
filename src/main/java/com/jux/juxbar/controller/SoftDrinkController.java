package com.jux.juxbar.controller;

import com.jux.juxbar.component.SoftDrinkApiInteractor;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.service.SoftDrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SoftDrinkController {

    private final SoftDrinkService softDrinkService;
    private final SoftDrinkApiInteractor softDrinkApiInteractor;

    @GetMapping("/softdrinks")
    public Iterable<SoftDrink> getSoftDrinks() {

        return softDrinkService.getDrinks();
    }

    @GetMapping("/softdrink/{id}")
    public Optional<SoftDrink> getSoftDrink(@PathVariable int id) {
        return softDrinkService.getDrink(id);
    }

    @GetMapping("softdrink/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        return softDrinkService.getImage(id);
    }

    @GetMapping("softdrink/{id}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable int id) {
        return softDrinkService.getPreview(id);
    }

    @GetMapping("/softdrinks/download")
    public ResponseEntity<String> downloadSoftDrinks() throws InterruptedException {

        softDrinkApiInteractor.checkUpdateAndDownload();
        return ResponseEntity.ok("Soft drinks à jour");

    }


    @GetMapping("softDrinks/downloadimages")
    public ResponseEntity<String> downloadSoftDrinksImages() {

        softDrinkApiInteractor.downloadImages();
        return ResponseEntity.ok("Images Soft à jour");
    }

    @GetMapping("softdrinks/downloadpreviews")
    public ResponseEntity<String> downloadSoftDrinkPreviews() {

        softDrinkApiInteractor.downloadPreviews();
        return ResponseEntity.ok("Previews Soft à jour");

    }

    @GetMapping("softdrinks/arraysize")
    public ResponseEntity<Long> getSoftDrinksArraySize() {
        return ResponseEntity.ok(softDrinkService.getAllDrinks().spliterator().getExactSizeIfKnown());
    }

}