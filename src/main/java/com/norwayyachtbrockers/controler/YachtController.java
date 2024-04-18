package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YahctSearchParametersDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.service.YachtService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
@RequestMapping("/yachts")
public class YachtController {

    private final YachtService yachtService;

    public YachtController(YachtService yachtService) {
        this.yachtService = yachtService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<YachtResponseDto> createYacht(
            @Valid
            @RequestPart("yachtData") YachtRequestDto dto,
            @RequestParam("mainImage") MultipartFile mainImageFile,
            @RequestParam("additionalImages") List<MultipartFile> additionalImageFiles) {

        YachtResponseDto savedYachtDto = yachtService.save(dto, mainImageFile, additionalImageFiles);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedYachtDto);
    }
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<YachtResponseDto> save(
//            @RequestPart("yachtData") YachtRequestDto dto,
//            @RequestPart("imageFile") MultipartFile imageFile
//    ) {
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(yachtService.save(dto, imageFile));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<YachtResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(yachtService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<YachtResponseDto>> getAll() {
        List<YachtResponseDto> yachts = yachtService.findAll();

        if (yachts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(yachts);
    }

    @GetMapping("/search")
    public List<YachtResponseDto> searchYachts(YahctSearchParametersDto searchParameters) {
        return yachtService.search(searchParameters);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<YachtResponseDto> updateYacht(
            @Valid @PathVariable Long id,
        @RequestPart(value = "yachtData", required = false) Optional<YachtRequestDto> dto,
            @RequestParam("mainImage") MultipartFile mainImageFile,
            @RequestParam("additionalImages") List<MultipartFile> additionalImageFiles) {

    YachtResponseDto updatedYachtDto = yachtService.update(dto.orElse(null), id, mainImageFile, additionalImageFiles);
        return new ResponseEntity<>(updatedYachtDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        yachtService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
