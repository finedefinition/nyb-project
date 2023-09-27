package com.norwayyachtbrockers.config;

import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.service.BoatServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer {
    private final BoatServiceImpl boatService;

    public DataInitializer(BoatServiceImpl boatService) {
        this.boatService = boatService;
    }

    @PostConstruct
    public void inject() {
        Boat boatOne = new Boat("Julia", new BigDecimal(80000), "Beneteau", 2016);
        boatService.save(boatOne);
    }
}
