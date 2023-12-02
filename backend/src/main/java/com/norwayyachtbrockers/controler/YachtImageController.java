package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.service.YachtImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/yachtImages")
public class YachtImageController {
    private final YachtImageService yachtImageService;

    public YachtImageController(YachtImageService yachtImageService) {
        this.yachtImageService = yachtImageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<YachtImage> save(
            @RequestPart("yachtImageData")YachtImageRequestDto dto,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(yachtImageService.save(dto, imageFile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<YachtImage> getById(@PathVariable Long id) {
        return ResponseEntity.ok(yachtImageService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<YachtImage>> getAll() {
        List<YachtImage> yachtImages = yachtImageService.findAll();

        if (yachtImages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(yachtImages);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<YachtImage> update(
            @PathVariable Long id,
            @RequestPart("yachtImageData") YachtImageRequestDto dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        return ResponseEntity.ok(yachtImageService.update(id, dto, imageFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        yachtImageService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the Town with ID: " + id );
    }
}
