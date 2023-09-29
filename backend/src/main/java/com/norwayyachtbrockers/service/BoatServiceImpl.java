package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.repository.BoatRepository;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BoatServiceImpl implements BoatService {
    private final BoatRepository boatRepository;

    public BoatServiceImpl(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    @Override
    public Boat save(Boat boat) {
        return boatRepository.save(boat);
    }

    @Override
    public Boat update(Long boatId, Boat updatedBoat) {
        // First, check if the boat with the given ID exists
        Boat existingBoat = boatRepository.findById(boatId)
                .orElseThrow(() -> new EntityNotFoundException("Boat not found with ID: " + boatId));

        // Update the existing boat entity with the new data
        existingBoat.setBoatName(updatedBoat.getBoatName());
        existingBoat.setBoatPrice(updatedBoat.getBoatPrice());
        existingBoat.setBoatBrand(updatedBoat.getBoatBrand());
        existingBoat.setBoatYear(updatedBoat.getBoatYear());

        // Save the updated boat entity
        return boatRepository.save(existingBoat);
    }

    @Override
    public Boat findById(Long theId) {
        return boatRepository.findById(theId).orElseThrow(
                () -> new RuntimeException("Can't get boat by id " + theId));
    }

    @Override
    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    @Override
    public List<Boat> findByName(String boatName) {
        return boatRepository.findByBoatName(boatName);
    }

    @Override
    public void deleteById(Long theId) {
        boatRepository.deleteById(theId);

    }
}
