package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.service.YachtModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/yachtModels")
public class YachtModelController {
    private final YachtModelService yachtModelService;

    public YachtModelController(YachtModelService yachtModelService) {
        this.yachtModelService = yachtModelService;
    }

    @GetMapping("/{theId}")
    public ResponseEntity<YachtModel> getYachtModelById(@PathVariable Long theId) {
        YachtModel yachtModel = yachtModelService.findById(theId);
        return ResponseEntity.ok(yachtModel);
    }

    @GetMapping
    public ResponseEntity<List<YachtModel>> getAllYachtModels() {
        List<YachtModel> yachtModels = yachtModelService.findAll();

        if (yachtModels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(yachtModels);
    }

    @PostMapping
    public ResponseEntity<YachtModel> createYachtModel(@RequestBody YachtModelRequestDto yachtModelRequestDto) {
        YachtModel createdYachtModel = yachtModelService.createYachtModel(yachtModelRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdYachtModel);
    }

    @DeleteMapping("/{yachtModelId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long yachtModelId) {
        yachtModelService.deleteById(yachtModelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("YachtModel-Delete-Status", "Success delete")
                .build();
    }

    @GetMapping("byKeelType/{keelTypeId}")
    public ResponseEntity<List<YachtModel>> getYachtModelByKeelTypeId(@PathVariable Long keelTypeId) {
        List<YachtModel> yachtModels = yachtModelService.findByKeelTypeId(keelTypeId);
        return ResponseEntity.ok(yachtModels);
    }

    @GetMapping("byFuelType/{fuelTypeId}")
    public ResponseEntity<List<YachtModel>> getYachtModelByFuelTypeId(@PathVariable Long fuelTypeId) {
        List<YachtModel> yachtModels = yachtModelService.findByKeelTypeId(fuelTypeId);
        return ResponseEntity.ok(yachtModels);
    }
}
