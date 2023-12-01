package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.service.YachtDetailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/yachtDetails")
public class YachtDetailController {

    private final YachtDetailService yachtDetailService;

    public YachtDetailController(YachtDetailService yachtDetailService) {
        this.yachtDetailService = yachtDetailService;
    }

    @PostMapping
    public ResponseEntity<YachtDetail> create(@Valid @RequestBody YachtDetailRequestDto dto) {
        return ResponseEntity.ok(yachtDetailService.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<YachtDetail> getById(@PathVariable Long id) {
        return ResponseEntity.ok(yachtDetailService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<YachtDetail>> getAll() {
        List<YachtDetail> yachtDetails = yachtDetailService.findAll();

        if (yachtDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(yachtDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<YachtDetail> update(@Valid @RequestBody YachtDetailRequestDto dto,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(yachtDetailService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        yachtDetailService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the Yacht Detail with ID: " + id);
    }
}
