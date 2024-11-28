package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.FullYachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.dto.request.YachtUpdateRequestDto;
import com.norwayyachtbrockers.dto.response.PaginatedYachtResponse;
import com.norwayyachtbrockers.dto.response.PaginationAndSortingParametersDto;
import com.norwayyachtbrockers.dto.response.YachtCrmFrontendResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.service.YachtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/yachts")
public class YachtController {

    private final YachtService yachtService;

    public YachtController(YachtService yachtService) {
        this.yachtService = yachtService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<YachtResponseDto> createYachtFromFullYachtRequestDto(
            @Valid
            @RequestPart("yachtData") FullYachtRequestDto dto,
            @RequestParam("mainImage") MultipartFile mainImageFile,
            @RequestParam("additionalImages") List<MultipartFile> additionalImageFiles) {

        YachtResponseDto savedYachtDto = yachtService.save(dto, mainImageFile, additionalImageFiles);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedYachtDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<YachtResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(yachtService.findId(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginatedYachtResponse> getAllYachts(
            @ModelAttribute PaginationAndSortingParametersDto paginationAndSortingParametersDto,
            @ModelAttribute YachtSearchParametersDto searchParametersDto) {

        PaginatedYachtResponse response = yachtService.getAllYachtsWithPaginationAndSearch(
                paginationAndSortingParametersDto, searchParametersDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<YachtResponseDto>> getAllFullDto() {
        List<YachtResponseDto> yachts = yachtService.findAll();

        if (yachts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(yachts);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<YachtResponseDto> updateYacht(
            @PathVariable Long id,
            @RequestPart(value = "yachtData", required = false)YachtUpdateRequestDto dto,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImageFile,
            @RequestParam(value = "additionalImages", required = false) List<MultipartFile> additionalImageFiles) {

        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        YachtResponseDto updatedYachtDto = yachtService.update(dto, id, mainImageFile, additionalImageFiles);
        return new ResponseEntity<>(updatedYachtDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        yachtService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/combined")
    public YachtCrmFrontendResponseDto getCombinedYachtData() {
        return yachtService.getCombinedYachtData();
    }

}
