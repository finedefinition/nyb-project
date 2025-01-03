package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.repository.projections.YachtModelProjection;
import com.norwayyachtbrockers.service.YachtModelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping
    public ResponseEntity<YachtModel> create(@Valid @RequestBody YachtModelRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(yachtModelService.saveYachtModel(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<YachtModel> getById(@PathVariable Long id) {
        return ResponseEntity.ok(yachtModelService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<YachtModelProjection>> getAll() {
        List<YachtModelProjection> yachtModels = yachtModelService.findAll();

        if (yachtModels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(yachtModels);
    }

    @PutMapping("/{id}")
    public ResponseEntity<YachtModel> update(@Valid @RequestBody YachtModelRequestDto dto,
                                                       @PathVariable Long id) {
        return ResponseEntity.ok(yachtModelService.updateYachtModel(dto, id));
    }

    @DeleteMapping("/{yachtModelId}")
    public ResponseEntity<?> delete(@PathVariable Long yachtModelId) {
        yachtModelService.deleteById(yachtModelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("byKeelType/{keelTypeId}")
    public ResponseEntity<List<YachtModel>> getYachtModelByKeelTypeId(@PathVariable Long keelTypeId) {
        List<YachtModel> yachtModels = yachtModelService.findByKeelType_Id(keelTypeId);
        return ResponseEntity.ok(yachtModels);
    }

    @GetMapping("byFuelType/{fuelTypeId}")
    public ResponseEntity<List<YachtModel>> getYachtModelByFuelTypeId(@PathVariable Long fuelTypeId) {
        List<YachtModel> yachtModels = yachtModelService.findByFuelType_Id(fuelTypeId);
        return ResponseEntity.ok(yachtModels);
    }
}
