package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.KeelRequestDto;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.repository.projections.KeelProjection;
import com.norwayyachtbrockers.service.KeelService;
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
@RequestMapping("/keels")
public class KeelController {

    private final KeelService keelService;

    public KeelController(KeelService keelService) {
        this.keelService = keelService;
    }

    @PostMapping
    public ResponseEntity<Keel> createKeel(@Valid @RequestBody KeelRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(keelService.saveKeel(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Keel> getKeelById(@PathVariable Long id) {
        return ResponseEntity.ok(keelService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<KeelProjection>> getAllKeels() {
        List<KeelProjection> keels = keelService.findAll();

        if (keels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(keels);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Keel> updateKeel(@Valid @RequestBody KeelRequestDto dto,
                                           @PathVariable Long id) {
        return ResponseEntity.ok(keelService.updateKeel(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        keelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
