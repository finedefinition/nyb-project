package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.model.Vessel;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface VesselService {
    Vessel save(Vessel vessel, MultipartFile imageFile);

    public Vessel updateVessel(Long vesselId, boolean featuredVessel, String vesselMake, String vesselModel,
                               BigDecimal vesselPrice, int vesselYear, String vesselLocationCountry,
                               String vesselLocationState, BigDecimal vesselLengthOverall, BigDecimal vesselBeam,
                               BigDecimal vesselDraft, int vesselCabin, int vesselBerth, String vesselKeelType,
                               String vesselFuelType, int engineQuantity, String vesselDescription, MultipartFile imageFile);

    Vessel findById(Long theId);

    List<Vessel> findAll();

    void deleteById(Long theId);
}
