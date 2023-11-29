package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.TownRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.service.TownService;
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
@RequestMapping("/towns")
public class TownController {
    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @PostMapping
    public ResponseEntity<TownResponseDto> createTown(@Valid @RequestBody TownRequestDto dto) {
        return ResponseEntity.ok(townService.saveTown(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TownResponseDto> getTownById(@PathVariable Long id) {
        return ResponseEntity.ok(townService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<TownResponseDto>> getAllTowns() {
        List<TownResponseDto> towns = townService.findAll();

        if (towns.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(towns);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TownResponseDto> updateTown(@Valid @RequestBody TownRequestDto dto,
                                                       @PathVariable Long id) {
        return ResponseEntity.ok(townService.updateTown(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        String town = townService.findId(id).getTownName();
        townService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the Town with ID:" + id +
                        " --> \"" + town + "\"");
    }
}

