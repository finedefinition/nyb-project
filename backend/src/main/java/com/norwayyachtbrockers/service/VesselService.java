package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.model.Vessel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VesselService {
    Vessel save(Vessel vessel, MultipartFile imageFile);

    Vessel update(Long vesselId, Vessel vessel, MultipartFile imageFile);

    Vessel findById(Long theId);

    List<Vessel> findAll();

    void deleteById(Long theId);
}
