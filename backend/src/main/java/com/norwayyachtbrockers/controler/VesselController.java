package com.norwayyachtbrockers.controler;
import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.service.VesselService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/vessels")
public class VesselController {
    private final VesselService vesselService;

    public VesselController(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @GetMapping("/{vesselId}")
    public ResponseEntity<Vessel> getBoatById(@PathVariable Long vesselId) {
        Vessel vessel = vesselService.findById(vesselId);

        if (vessel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(vessel);
    }

    @PostMapping
    public ResponseEntity<Vessel> createVessel(
//            @RequestParam("featuredVessel") boolean featuredVessel,
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
            @RequestParam("engineQuantity") int engineQuantity,
            @RequestParam("vesselDescription") String vesselDescription,
            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        // Log the received data to help with debugging
        System.out.println("Received Data:");
//        System.out.println("featuredVessel: " + featuredVessel);
        System.out.println("vesselMake: " + vesselMake);
        System.out.println("vesselModel: " + vesselModel);
        System.out.println("vesselPrice: " + vesselPrice);
        System.out.println("vesselYear: " + vesselYear);
        System.out.println("vesselLocationCountry: " + vesselLocationCountry);
        System.out.println("vesselLocationState: " + vesselLocationState);
        System.out.println("vesselLengthOverall: " + vesselLengthOverall);
        System.out.println("vesselBeam: " + vesselBeam);
        System.out.println("vesselDraft: " + vesselDraft);
        System.out.println("vesselCabin: " + vesselCabin);
        System.out.println("vesselBerth: " + vesselBerth);
        System.out.println("vesselKeelType: " + vesselKeelType);
        System.out.println("vesselFuelType: " + vesselFuelType);
        System.out.println("engineQuantity: " + engineQuantity);
        System.out.println("vesselDescription: " + vesselDescription);
        System.out.println("imageFile: " + imageFile.getOriginalFilename()); // Log the filename of the uploaded file
        // Create a new Vessel object and set its properties
        Vessel newVessel = new Vessel();
//        newVessel.setFeaturedVessel(featuredVessel);
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
        newVessel.setEngineQuantity(engineQuantity);
        newVessel.setVesselDescription(vesselDescription);
        newVessel.setCreatedAt(LocalDateTime.now());

        // Save the Vessel object with image
        Vessel createdVessel = vesselService.save(newVessel, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdVessel);
    }
}
