package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.model.Boat;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public interface BoatService {
    Boat save(Boat boat, MultipartFile imageFile);

    Boat update(Long boatId, Boat boat, MultipartFile imageFile);

    Boat findById(Long theId);

    List<Boat> findAll();

    List<Boat> findByName(String boatName);

    void deleteById(Long theId);
}
