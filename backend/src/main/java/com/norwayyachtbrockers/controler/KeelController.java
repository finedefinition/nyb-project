package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.repository.KeelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/keels")
public class KeelController {

    private final KeelRepository keelRepository;

    public KeelController(KeelRepository keelRepository) {
        this.keelRepository = keelRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Keel> getById(@PathVariable Long id) {
        return keelRepository.findById(id)
                .map(ResponseEntity::ok)  // Wrap the Fuel object in a ResponseEntity if found
                .orElse(ResponseEntity.notFound().build());  // Return 404 Not Found if not found
    }

    @GetMapping
    public ResponseEntity<List<Keel>> getAllKeels() {
        List<Keel> keels = keelRepository.findAll();

        if (keels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(keels);
    }

}
