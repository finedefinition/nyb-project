package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.FullYachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.dto.response.PaginatedYachtCrmResponse;
import com.norwayyachtbrockers.dto.response.PaginatedYachtResponse;
import com.norwayyachtbrockers.dto.response.PaginationAndSortingParametersDto;
import com.norwayyachtbrockers.dto.response.YachtCrmFrontendResponseDto;
import com.norwayyachtbrockers.dto.response.YachtCrmResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.service.YachtService;
import jakarta.validation.Valid;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/yachts")
public class YachtController {

    private final YachtService yachtService;

    public YachtController(YachtService yachtService) {
        this.yachtService = yachtService;
    }

    @PostMapping(value = "/full-dto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<YachtResponseDto> createYachtFromFullYachtRequestDto(
            @Valid
            @RequestPart("yachtData") FullYachtRequestDto dto,
            @RequestParam("mainImage") MultipartFile mainImageFile,
            @RequestParam("additionalImages") List<MultipartFile> additionalImageFiles) {

        YachtResponseDto savedYachtDto = yachtService.save(dto, mainImageFile, additionalImageFiles);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedYachtDto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<YachtResponseDto> createYacht(
            @Valid
            @RequestPart("yachtData") YachtRequestDto dto,
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
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = "ascend") String orderBy,
            YachtSearchParametersDto searchParameters) {

        // Translate "descend" to "desc" and "ascend" to "asc"
        if ("descend".equalsIgnoreCase(orderBy)) {
            orderBy = "desc";
        } else if ("ascend".equalsIgnoreCase(orderBy)) {
            orderBy = "asc";
        }

        PaginationAndSortingParametersDto paginationAndSortingParameters = new PaginationAndSortingParametersDto();
        paginationAndSortingParameters.setPage(page);
        paginationAndSortingParameters.setSortBy(sortBy);
        paginationAndSortingParameters.setOrderBy(orderBy);

        PaginatedYachtResponse response = yachtService.getAllYachtsWithPaginationAndSearch(paginationAndSortingParameters, searchParameters);
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

    @GetMapping("/search")
    public List<YachtResponseDto> searchYachts(YachtSearchParametersDto searchParameters) {
        return yachtService.search(searchParameters);
    }

    @GetMapping("/paginated/search-crm")
    public List<YachtCrmResponseDto> searchYachtsCrm(YachtSearchParametersDto searchParameters) {
        return yachtService.searchForCrm(searchParameters);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

//    @GetMapping(value = "/crm", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PaginatedYachtCrmResponse> getPaginatedYachts(
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
//            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
//            YachtSearchParametersDto searchParameters) {
//
//        PaginatedYachtCrmResponse response = yachtService.getYachtsWithPagination(page, searchParameters, sortBy, sortDirection);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping(value = "/paginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginatedYachtCrmResponse> getPaginatedYachts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = "ascend") String orderBy,
            YachtSearchParametersDto searchParameters) {

        // Translate "descend" to "desc" and "ascend" to "asc"
        if ("descend".equalsIgnoreCase(orderBy)) {
            orderBy = "desc";
        } else if ("ascend".equalsIgnoreCase(orderBy)) {
            orderBy = "asc";
        }

        PaginationAndSortingParametersDto paginationAndSortingParameters = new PaginationAndSortingParametersDto();
        paginationAndSortingParameters.setPage(page);
        paginationAndSortingParameters.setSortBy(sortBy);
        paginationAndSortingParameters.setOrderBy(orderBy);

        PaginatedYachtCrmResponse response = yachtService.getYachtsWithPaginationAndSearch(paginationAndSortingParameters, searchParameters);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/combined")
    public YachtCrmFrontendResponseDto getCombinedYachtData() {
        return yachtService.getCombinedYachtData();
    }

}
