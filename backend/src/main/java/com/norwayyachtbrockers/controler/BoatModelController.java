package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.BoatModel;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.repository.BoatModelRepository;
import com.norwayyachtbrockers.repository.BoatRepository;
import com.norwayyachtbrockers.service.BoatModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/boatmodels")
public class BoatModelController {
    private final BoatModelService boatModelService;

    public BoatModelController(BoatModelService boatModelService) {
        this.boatModelService = boatModelService;
    }

    @GetMapping("/{theId}")
    public ResponseEntity<BoatModel> getBoatModelById(@PathVariable Long theId) {
        BoatModel boatModel = boatModelService.findById(theId);
        return ResponseEntity.ok(boatModel);
    }

    @GetMapping
    public ResponseEntity<List<BoatModel>> getAllBoatModels() {
        List<BoatModel> boatModels = boatModelService.findAll();

        if (boatModels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(boatModels);
    }

    @DeleteMapping("/{boatModelId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long boatModelId) {
        boatModelService.deleteById(boatModelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("BoatModel-Delete-Status", "Success delete")
                .build();
    }
}
