package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.service.BoatService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boats")
public class BoatController {
    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @GetMapping("/{boatId}")
    public Boat getBoat(@PathVariable Long boatId) {

        Boat boat = boatService.findById(boatId);

        if (boat == null) {
            throw new RuntimeException("Boat id not found - " + boatId);
        }

        return boat;
    }

    @GetMapping("/")
    public List<Boat> findAll() {
        return boatService.findAll();
    }

}
