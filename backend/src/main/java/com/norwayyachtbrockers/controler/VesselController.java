package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.mapper.VesselMapper;
import com.norwayyachtbrockers.dto.mapper.VesselShortMapper;
import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.dto.response.VesselShortResponseDto;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.service.VesselService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vessels")
public class VesselController {
    private final VesselService vesselService;
    private final VesselShortMapper vesselShortMapper;
    private final VesselMapper vesselMapper;

    public VesselController(VesselService vesselService,
                            VesselShortMapper vesselShortMapper, VesselMapper vesselMapper) {
        this.vesselService = vesselService;
        this.vesselShortMapper = vesselShortMapper;
        this.vesselMapper = vesselMapper;
    }

    @GetMapping("/{vesselId}")
    public ResponseEntity<Vessel> getVesselById(@PathVariable Long vesselId) {
        Vessel vessel = vesselService.findById(vesselId);
        return ResponseEntity.ok(vessel);
    }

    @GetMapping
    public ResponseEntity<List<Vessel>> getAllVessels() {
        List<Vessel> vessels = vesselService.findAll();

        if (vessels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(vessels);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Vessel> createVessel(
            @RequestPart("vesselData") VesselRequestDto vesselData,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        Vessel newVessel = new Vessel();
        vesselMapper.updateVesselFromDto(newVessel, vesselData);
        Vessel createdVessel = vesselService.save(newVessel, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVessel);
    }

    @PutMapping(value = "/{vesselId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Vessel> updateVessel(
            @PathVariable Long vesselId,
            @RequestPart("vesselData") VesselRequestDto vesselData,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {

        Vessel updatedVessel = vesselService.update(vesselId, vesselData, imageFile);
        return ResponseEntity.ok(updatedVessel);
    }

    @DeleteMapping("/{vesselId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long vesselId) {
        vesselService.deleteById(vesselId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("Vessel-Delete-Status", "Success delete")
                .build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        vesselService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("Vessel-Delete-Status", "Success delete")
                .build();
    }


    @GetMapping("/cards")
    public ResponseEntity<List<VesselShortResponseDto>> findAllShortVesselCards() {
        List<Vessel> vessels = vesselService.findAll();

        List<VesselShortResponseDto> vesselShortResponseDtoList = vessels.stream()
                .map(vesselShortMapper::toVesselShortResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(vesselShortResponseDtoList);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
