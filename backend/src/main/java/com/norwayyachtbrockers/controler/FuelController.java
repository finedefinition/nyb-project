package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
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
        Fuel fuel = fuelRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(
                        String.format("Keel with ID: %d not found", id)));

        return ResponseEntity.ok(fuel);
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
