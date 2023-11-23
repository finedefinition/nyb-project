package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.repository.FuelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/fuels")
public class FuelController {
    private final FuelRepository fuelRepository;

    public FuelController(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fuel> getById(@PathVariable Long id) {
        return fuelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Fuel>> getAllFuels() {
        List<Fuel> fuels = fuelRepository.findAll();

        if (fuels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(fuels);
    }
}
