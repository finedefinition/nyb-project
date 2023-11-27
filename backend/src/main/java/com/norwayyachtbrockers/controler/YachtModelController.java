package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.YachtModelRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.service.YachtModelService;
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
import java.util.Optional;

@RestController
@RequestMapping("/yachtModels")
public class YachtModelController {
    private final YachtModelService yachtModelService;

    public YachtModelController(YachtModelService yachtModelService) {
        this.yachtModelService = yachtModelService;
    }

    @PostMapping
    public ResponseEntity<YachtModel> createYachtModel(@RequestBody YachtModelRequestDto dto) {
        return ResponseEntity.ok(yachtModelService.saveYachtModel(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<YachtModel> updateYachtModel(@RequestBody YachtModelRequestDto dto, @PathVariable Long id) {
        try {
            YachtModel updatedYachtModel = yachtModelService.updateYachtModel(dto, id);
            return ResponseEntity.ok(updatedYachtModel);
        } catch (AppEntityNotFoundException e) {
            // Handle the case where the yacht model or related entities are not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping
    public List<YachtModel> getAllYachtModels() {
        return yachtModelService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<YachtModel> getYachtModelById(@PathVariable Long id) {
        Optional<YachtModel> yachtModelOptional = yachtModelService.findId(id);

        return yachtModelOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{yachtModelId}")
    public ResponseEntity<String> deleteById(@PathVariable Long yachtModelId) {
        yachtModelService.deleteById(yachtModelId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the Yacht Model with ID:" + yachtModelId);
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
