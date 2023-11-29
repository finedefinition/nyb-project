package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.CountryRequestDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.service.CountryService;
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
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@Valid @RequestBody CountryRequestDto dto) {
        return ResponseEntity.ok(countryService.saveCountry(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.findAll();

        if (countries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(countries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@Valid @RequestBody CountryRequestDto dto,
                                           @PathVariable Long id) {
        return ResponseEntity.ok(countryService.updateCountry(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        String country = countryService.findId(id).getName();
        countryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the Country with ID:" + id
                        + " --> \"" + country + "\"");
    }
}
