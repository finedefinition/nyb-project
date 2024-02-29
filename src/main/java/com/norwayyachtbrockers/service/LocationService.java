package com.norwayyachtbrockers.service;

import com.norwayyachtbrockers.model.Location;
import java.util.List;

public interface LocationService {
    Location findId(Long id);
    List<Location> findAll();

}
