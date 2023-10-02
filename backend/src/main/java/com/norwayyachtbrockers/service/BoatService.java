package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.model.Boat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BoatService {
    public Boat save(Boat boat, MultipartFile imageFile);

    public Boat update(Long boatId, Boat updatedBoat);

    Boat findById(Long theId);

    List<Boat> findAll();

    List<Boat> findByName(String boatName);

    void deleteById(Long theId);
}
