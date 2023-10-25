package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.mapper.VesselShortMapper;
import com.norwayyachtbrockers.dto.response.VesselShortResponseDto;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import com.norwayyachtbrockers.service.VesselService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vessels")
public class VesselController {
    private final VesselService vesselService;
    private final VesselShortMapper vesselShortMapper;

    public VesselController(VesselService vesselService, VesselShortMapper vesselShortMapper) {
        this.vesselService = vesselService;
        this.vesselShortMapper = vesselShortMapper;
    }

    @GetMapping("/{vesselId}")
    public ResponseEntity<Vessel> geVesselById(@PathVariable Long vesselId) {
        Vessel vessel = vesselService.findById(vesselId);

        if (vessel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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

    @PostMapping
    public ResponseEntity<Vessel> createVessel(
            @RequestParam("featuredVessel") boolean featuredVessel,
            @RequestParam("vesselMake") String vesselMake,
            @RequestParam("vesselModel") String vesselModel,
            @RequestParam("vesselPrice") BigDecimal vesselPrice,
            @RequestParam("vesselYear") int vesselYear,
            @RequestParam("vesselLocationCountry") String vesselLocationCountry,
            @RequestParam("vesselLocationState") String vesselLocationState,
            @RequestParam("vesselLengthOverall") BigDecimal vesselLengthOverall,
            @RequestParam("vesselBeam") BigDecimal vesselBeam,
            @RequestParam("vesselDraft") BigDecimal vesselDraft,
            @RequestParam("vesselCabin") int vesselCabin,
            @RequestParam("vesselBerth") int vesselBerth,
            @RequestParam("vesselKeelType") String vesselKeelType,
            @RequestParam("vesselFuelType") String vesselFuelType,
            @RequestParam("keelType") KeelType keelType,
            @RequestParam("fuelType") FuelType fuelType,
            @RequestParam("engineQuantity") int engineQuantity,
            @RequestParam("vesselDescription") String vesselDescription,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        Vessel newVessel = mapVesselFromRequestParams(
                featuredVessel, vesselMake, vesselModel, vesselPrice, vesselYear, vesselLocationCountry,
                vesselLocationState, vesselLengthOverall, vesselBeam, vesselDraft, vesselCabin, vesselBerth,
                vesselKeelType, vesselFuelType, keelType, fuelType, engineQuantity, vesselDescription
        );
        newVessel.setCreatedAt(LocalDateTime.now());
        Vessel createdVessel = vesselService.save(newVessel, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVessel);
    }

    @PutMapping("/{vesselId}")
    public ResponseEntity<Vessel> updateVessel(
            @PathVariable Long vesselId,
            @RequestParam("featuredVessel") boolean featuredVessel,
            @RequestParam("vesselMake") String vesselMake,
            @RequestParam("vesselModel") String vesselModel,
            @RequestParam("vesselPrice") BigDecimal vesselPrice,
            @RequestParam("vesselYear") int vesselYear,
            @RequestParam("vesselLocationCountry") String vesselLocationCountry,
            @RequestParam("vesselLocationState") String vesselLocationState,
            @RequestParam("vesselLengthOverall") BigDecimal vesselLengthOverall,
            @RequestParam("vesselBeam") BigDecimal vesselBeam,
            @RequestParam("vesselDraft") BigDecimal vesselDraft,
            @RequestParam("vesselCabin") int vesselCabin,
            @RequestParam("vesselBerth") int vesselBerth,
            @RequestParam("vesselKeelType") String vesselKeelType,
            @RequestParam("vesselFuelType") String vesselFuelType,
            @RequestParam("keelType") KeelType keelType,
            @RequestParam("fuelType") FuelType fuelType,
            @RequestParam("engineQuantity") int engineQuantity,
            @RequestParam("vesselDescription") String vesselDescription,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        Vessel updated = vesselService.updateVessel(
                vesselId, featuredVessel, vesselMake, vesselModel, vesselPrice, vesselYear,
                vesselLocationCountry, vesselLocationState, vesselLengthOverall, vesselBeam,
                vesselDraft, vesselCabin, vesselBerth, vesselKeelType, vesselFuelType, keelType,
                fuelType, engineQuantity, vesselDescription, imageFile
        );
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{vesselId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long vesselId) {
        vesselService.deleteById(vesselId);
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

    private Vessel mapVesselFromRequestParams(
            boolean featuredVessel, String vesselMake, String vesselModel, BigDecimal vesselPrice,
            int vesselYear, String vesselLocationCountry, String vesselLocationState,
            BigDecimal vesselLengthOverall, BigDecimal vesselBeam, BigDecimal vesselDraft,
            int vesselCabin, int vesselBerth, String vesselKeelType, String vesselFuelType, KeelType keelType,
            FuelType fuelType, int engineQuantity, String vesselDescription
    ) {
        Vessel newVessel = new Vessel();
        newVessel.setFeaturedVessel(featuredVessel);
        newVessel.setVesselMake(vesselMake);
        newVessel.setVesselModel(vesselModel);
        newVessel.setVesselPrice(vesselPrice);
        newVessel.setVesselYear(vesselYear);
        newVessel.setVesselLocationCountry(vesselLocationCountry);
        newVessel.setVesselLocationState(vesselLocationState);
        newVessel.setVesselLengthOverall(vesselLengthOverall);
        newVessel.setVesselBeam(vesselBeam);
        newVessel.setVesselDraft(vesselDraft);
        newVessel.setVesselCabin(vesselCabin);
        newVessel.setVesselBerth(vesselBerth);
        newVessel.setVesselKeelType(vesselKeelType);
        newVessel.setVesselFuelType(vesselFuelType);
        newVessel.setKeelType(keelType);
        newVessel.setFuelType(fuelType);
        newVessel.setEngineQuantity(engineQuantity);
        newVessel.setVesselDescription(vesselDescription);

        return newVessel;
    }
}
