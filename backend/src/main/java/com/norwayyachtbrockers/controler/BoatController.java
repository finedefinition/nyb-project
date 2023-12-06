package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.mapper.BoatCreationMapper;
import com.norwayyachtbrockers.dto.mapper.BoatShortMapper;
import com.norwayyachtbrockers.dto.response.BoatShortResponseDto;
import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.service.BoatService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/boats")
public class BoatController {
    private final BoatService boatService;

    private final BoatShortMapper boatShortMapper;

    private final BoatCreationMapper boatCreationMapper;

    public BoatController(BoatService boatService, BoatShortMapper boatShortMapper, BoatCreationMapper boatCreationMapper) {
        this.boatService = boatService;
        this.boatShortMapper = boatShortMapper;
        this.boatCreationMapper = boatCreationMapper;
    }

    @GetMapping("/{boatId}")
    public ResponseEntity<Boat> getBoatById(@PathVariable Long boatId) {
        Boat boat = boatService.findById(boatId);

        if (boat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(boat);
    }

    @GetMapping
    public ResponseEntity<List<Boat>> getAllBoats() {
        List<Boat> boats = boatService.findAll();

        if (boats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(boats);
    }

    @PostMapping
    public ResponseEntity<Boat> createBoat(
            @RequestParam("boatName") String boatName,
            @RequestParam("boatPrice") BigDecimal boatPrice,
            @RequestParam("boatBrand") String boatBrand,
            @RequestParam("boatYear") int boatYear,
            @RequestParam("boatPlace") String boatPlace,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        Boat newBoat = new Boat();
        newBoat.setBoatName(boatName);
        newBoat.setBoatPrice(boatPrice);
        newBoat.setBoatBrand(boatBrand);
        newBoat.setBoatYear(boatYear);
        newBoat.setBoatPlace(boatPlace);

        // Save the Boat object with image
        Boat createdBoat = boatService.save(newBoat, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoat);
    }

    // PUT update an existing boat (replace entire resource)
    @PutMapping("/{boatId}")
    public ResponseEntity<Boat> update(
            @PathVariable Long boatId,
            @RequestParam("boatName") String boatName,
            @RequestParam("boatPrice") BigDecimal boatPrice,
            @RequestParam("boatBrand") String boatBrand,
            @RequestParam("boatYear") int boatYear,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        // Create a new Boat object and set its properties
        Boat updatedBoat = new Boat();
        updatedBoat.setBoatName(boatName);
        updatedBoat.setBoatPrice(boatPrice);
        updatedBoat.setBoatBrand(boatBrand);
        updatedBoat.setBoatYear(boatYear);

        // Update the Boat object with the image file
        Boat updated = boatService.update(boatId, updatedBoat, imageFile);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{boatId}")
    public ResponseEntity<Void> deleteBoat(@PathVariable Long boatId) {
        boatService.deleteById(boatId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/featured")
    public ResponseEntity<List<BoatShortResponseDto>> findAllShortsBoat() {
        List<Boat> boats = boatService.findAll(); // Replace with your service method to fetch boats

        List<BoatShortResponseDto> boatShortResponseDtos = boats.stream()
                .map(boatShortMapper::toBoatShortResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(boatShortResponseDtos);
    }
}
