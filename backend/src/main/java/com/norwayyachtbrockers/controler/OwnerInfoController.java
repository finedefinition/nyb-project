package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.service.OwnerInfoService;
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
@RequestMapping("/ownerInfos")
public class OwnerInfoController {

    private final OwnerInfoService ownerInfoService;

    public OwnerInfoController(OwnerInfoService ownerInfoService) {
        this.ownerInfoService = ownerInfoService;
    }

    @PostMapping
    public ResponseEntity<OwnerInfo> create(@Valid @RequestBody OwnerInfoRequestDto dto) {
        return ResponseEntity.ok(ownerInfoService.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerInfo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerInfoService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<OwnerInfo>> getAll() {
        List<OwnerInfo> ownerInfos = ownerInfoService.findAll();

        if (ownerInfos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(ownerInfos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerInfo> updateTown(@Valid @RequestBody OwnerInfoRequestDto dto,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(ownerInfoService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        String message = ownerInfoService.findId(id).getEmail();
        ownerInfoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the Owner info with ID:" + id +
                        " --> \"e-mail: " + message + "\"");
    }
}
