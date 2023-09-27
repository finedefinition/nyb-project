package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.model.Boat;

import java.util.List;

public interface BoatService {
    Boat save(Boat boat);

    Boat update(Boat boat);

    Boat findById(Long theId);

    List<Boat> findAll();

    List<Boat> findByName(String boatName);

    void deleteById(Long theId);
}
