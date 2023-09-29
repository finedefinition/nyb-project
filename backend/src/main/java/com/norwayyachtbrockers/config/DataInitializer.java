package com.norwayyachtbrockers.config;

import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.service.BoatServiceImpl;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final BoatServiceImpl boatService;

    public DataInitializer(BoatServiceImpl boatService) {
        this.boatService = boatService;
    }

    @PostConstruct
    public void inject() {
        Boat boatOne = new Boat("Serenity", new BigDecimal(180000),
                "Amel", 2016, LocalDateTime.now(Clock.systemUTC()));
        boatService.save(boatOne);
    }
}
