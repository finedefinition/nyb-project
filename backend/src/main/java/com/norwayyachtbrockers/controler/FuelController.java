package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.FuelRequestDto;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.service.FuelService;
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
@RequestMapping("/fuels")
public class FuelController {
    private final FuelService fuelService;

    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @PostMapping
    public ResponseEntity<Fuel> createFuel(@Valid @RequestBody FuelRequestDto dto) {
        return ResponseEntity.ok(fuelService.saveFuel(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fuel> getFuelById(@PathVariable Long id) {
        Fuel fuel = fuelService.findId(id);
        return ResponseEntity.ok(fuel);
    }

    @GetMapping
    public ResponseEntity<List<Fuel>> getAllFuels() {
        List<Fuel> fuels = fuelService.findAll();

        if (fuels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(fuels);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fuel> updateKeel(@Valid @RequestBody FuelRequestDto dto,
                                                 @PathVariable Long id) {
        return ResponseEntity.ok(fuelService.updateFuel(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        fuelService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the Fuel with ID:" + id);
    }
}
