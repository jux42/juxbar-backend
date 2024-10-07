package com.jux.juxbar.controller;

import com.jux.juxbar.component.SoftDrinkApiInteractor;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.service.SoftDrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SoftDrinkController {

    private final SoftDrinkService softDrinkService;
    private final SoftDrinkApiInteractor softDrinkApiInteractor;

    @GetMapping("/softdrinks")
    public ResponseEntity<Iterable<SoftDrink>> getSoftDrinks(

            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {


        if (page != null && limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            return ResponseEntity.ok(softDrinkService.getDrinks(pageable));
        }
        return ResponseEntity.ok(softDrinkService.getAllDrinks());

    }

    @GetMapping("/softdrink/{id}")
    public Optional<SoftDrink> getSoftDrink(@PathVariable int id) {
        return softDrinkService.getDrink(id);
    }

    @GetMapping("softdrink/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {

        byte[] imageBytes = softDrinkService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "public, max-age=31536000");
        headers.add("Content-Type", "image/jpeg");

        return new  ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("softdrink/{id}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable int id) {
        byte[] imageBytes = softDrinkService.getPreview(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "public, max-age=31536000");
        headers.add("Content-Type", "image/jpeg");

        return new  ResponseEntity<>(imageBytes, headers, HttpStatus.OK);    }

    @GetMapping("/softdrinks/download")
    public ResponseEntity<String> downloadSoftDrinks() throws InterruptedException {

        softDrinkApiInteractor.checkUpdateAndDownload();
        return ResponseEntity.ok("Soft drinks à jour");

    }


    @GetMapping("softdrinks/downloadimages")
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