package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.YachtImageRequestDto;
import com.norwayyachtbrockers.dto.response.YachtImageResponseDto;
import com.norwayyachtbrockers.service.YachtImageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<YachtImageResponseDto>> uploadMultipleImages(
            @Valid
            @RequestPart("yachtData") YachtImageRequestDto dto,
            @RequestParam("images") List<MultipartFile> files) {
        List<YachtImageResponseDto> yachtImages = yachtImageService.saveMultipleImages(dto, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(yachtImages);
    }


    @GetMapping("/{id}")
    public ResponseEntity<YachtImageResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(yachtImageService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<YachtImageResponseDto>> getAll() {
        List<YachtImageResponseDto> yachtImages = yachtImageService.findAll();

        if (yachtImages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(yachtImages);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<YachtImageResponseDto> update(
            @Valid
            @PathVariable Long id,
            @RequestPart("yachtData") YachtImageRequestDto dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        return ResponseEntity.ok(yachtImageService.update(id, dto, imageFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        yachtImageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
