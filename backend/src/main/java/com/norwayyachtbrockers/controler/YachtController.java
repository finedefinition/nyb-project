package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.service.YachtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<YachtResponseDto> save(
            @RequestPart("yachtData") YachtRequestDto dto,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(yachtService.save(dto, imageFile));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        yachtService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the Yacht Model with ID:" + id);
    }
}
