package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.service.BoatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
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

@RestController
@RequestMapping("/boats")
public class BoatController {
    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
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
    public ResponseEntity<Map<String, Object>> findAll() {
        List<Boat> boats = boatService.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("content", boats);
        response.put("page", createPaginationInfo(boats.size(), 0, boats.size())); // Customize this as needed

        return ResponseEntity.ok(response);
    }

    private Map<String, Integer> createPaginationInfo(int size, int number, int totalElements) {
        Map<String, Integer> paginationInfo = new HashMap<>();
        paginationInfo.put("size", size);
        paginationInfo.put("number", number);
        paginationInfo.put("totalElements", totalElements);
        paginationInfo.put("totalPages", (int) Math.ceil((double) totalElements / size));
        return paginationInfo;
    }

    @PostMapping("/create")
    public ResponseEntity<Boat> createBoat(@RequestBody Boat newBoat) {
        Boat createdBoat = boatService.save(newBoat);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoat);
    }

    // PUT update an existing boat (replace entire resource)
    @PutMapping("/{boatId}")
    public ResponseEntity<Boat> updateBoat(@PathVariable Long boatId, @RequestBody Boat updatedBoat) {
        Boat updated = boatService.update(boatId, updatedBoat);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{boatId}")
    public ResponseEntity<Void> deleteBoat(@PathVariable Long boatId) {
        boatService.deleteById(boatId);
        return ResponseEntity.noContent().build();
    }
}
