package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.repository.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable Long id) {
        return countryRepository.findById(id)
                .map(ResponseEntity::ok)  // Wrap the Fuel object in a ResponseEntity if found
                .orElse(ResponseEntity.notFound().build());  // Return 404 Not Found if not found
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryRepository.findAll();

        if (countries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(countries);
    }
}
