package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.Location;
import com.norwayyachtbrockers.repository.LocationRepository;
import com.norwayyachtbrockers.service.LocationService;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location findId(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new AppEntityNotFoundException(String
                .format("Location with ID: %d not found", id)));
    }

    @Override
    public List<Location> findAll() {
        List<Location> locations = locationRepository.findAll();
        locations.sort(Comparator.comparing(Location::getId));
        return locations;
    }
}
