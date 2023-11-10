package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.repository.BoatModelRepository;
import com.norwayyachtbrockers.repository.BoatRepository;
import com.norwayyachtbrockers.service.BoatModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boatmodels")
public class BoatModelController {
    private final BoatModelService boatModelService;

    private final BoatRepository boatRepository;

    public BoatModelController(BoatModelService boatModelService, BoatRepository boatRepository) {
        this.boatModelService = boatModelService;
        this.boatRepository = boatRepository;
    }

    @DeleteMapping("/{boatModelId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long boatModelId) {
        boatModelService.deleteById(boatModelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("BoatModel-Delete-Status", "Success delete")
                .build();
    }
}
