package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.repository.CountryRepository;
import com.norwayyachtbrockers.repository.TownRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final TownRepository townRepository;

    private final CountryRepository countryRepository;

    public TownController(TownRepository townRepository, CountryRepository countryRepository) {
        this.townRepository = townRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Town> getById(@PathVariable Long id) {
        return townRepository.findById(id)
                .map(ResponseEntity::ok)  // Wrap the Fuel object in a ResponseEntity if found
                .orElse(ResponseEntity.notFound().build());  // Return 404 Not Found if not found
    }

    @GetMapping
    public ResponseEntity<List<Town>> getAllTowns() {
        List<Town> towns = townRepository.findAll();

        if (towns.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(towns);
    }

    @PostMapping
    public ResponseEntity<?> createTown(@RequestBody Town town) {
        // Find the country by countryId
        Country country = countryRepository.findById(town.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

        // Set the country to the town entity
        town.setCountry(country);

        // Save the town
        Town savedTown = townRepository.save(town);
        return ResponseEntity.ok(savedTown);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Town> updateTown(@PathVariable Long id, @RequestBody Town townDetails) {
        Town town = townRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException("Town not found with id : " + id));

        // Update town details. Here, only name is updated. Add other fields if needed.
        town.setName(townDetails.getName());


        // If countryId is being updated, ensure the new country exists.
        if (townDetails.getCountryId() != null && !town.getCountry().getId().equals(townDetails.getCountryId())) {
            Country newCountry = countryRepository.findById(townDetails.getCountryId())
                    .orElseThrow(() -> new AppEntityNotFoundException("Country not found with id : " + townDetails.getCountryId()));
            town.setCountry(newCountry);
        }

        final Town updatedTown = townRepository.save(town);
        return ResponseEntity.ok(updatedTown);
    }
}