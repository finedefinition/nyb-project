package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.model.BoatModel;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface BoatService {
    Boat save(Boat boat, MultipartFile imageFile);

    Boat update(Long boatId, Boat boat, MultipartFile imageFile);

    Boat findById(Long theId);

    List<Boat> findAll();

    void deleteById(Long theId);


}
