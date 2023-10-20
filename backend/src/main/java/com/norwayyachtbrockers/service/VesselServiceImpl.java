package com.norwayyachtbrockers.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.repository.VesselRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VesselServiceImpl implements VesselService {
    private final VesselRepository vesselRepository;
    private final AmazonS3 amazonS3;
    private final String s3BucketName;

    public VesselServiceImpl(VesselRepository vesselRepository, AmazonS3 amazonS3,
                             @Value("${s3.bucket.name}")String s3BucketName) {
        this.vesselRepository = vesselRepository;
        this.amazonS3 = amazonS3;
        this.s3BucketName = s3BucketName;
    }

    @Override
    public Vessel save(Vessel vessel, MultipartFile imageFile) {
        vessel.setCreatedAt(LocalDateTime.now());
        // Generate a unique key for the image in S3 (e.g., using UUID)
        String imageKey = UUID.randomUUID().toString();

        try {
            // Upload the image to S3
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imageFile.getContentType());

            amazonS3.putObject(s3BucketName, imageKey, imageFile.getInputStream(), metadata);

            // Set the image key in the boat entity
            vessel.setImageKey(imageKey);

        } catch (IOException e) {
            // Handle the exception (e.g., log it or throw a custom exception)
            throw new RuntimeException("Failed to upload image to S3: " + e.getMessage());
        }

        return vesselRepository.save(vessel);
    }

    @Override
    public Vessel updateVessel(Long vesselId, boolean featuredVessel, String vesselMake, String vesselModel,
                               BigDecimal vesselPrice, int vesselYear, String vesselLocationCountry,
                               String vesselLocationState, BigDecimal vesselLengthOverall, BigDecimal vesselBeam,
                               BigDecimal vesselDraft, int vesselCabin, int vesselBerth, String vesselKeelType,
                               String vesselFuelType, int engineQuantity, String vesselDescription, MultipartFile imageFile) {
        Vessel existingVessel = vesselRepository.findById(vesselId)
                .orElseThrow(() -> new EntityNotFoundException("Vessel not found with ID: " + vesselId));

        String newImageKey = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            newImageKey = uploadImageToS3(imageFile);
        }

        // Update the vessel attributes
        existingVessel.setFeaturedVessel(featuredVessel);
        existingVessel.setVesselMake(vesselMake);
        existingVessel.setVesselModel(vesselModel);
        existingVessel.setVesselPrice(vesselPrice);
        existingVessel.setVesselYear(vesselYear);
        existingVessel.setVesselLocationCountry(vesselLocationCountry);
        existingVessel.setVesselLocationState(vesselLocationState);
        existingVessel.setVesselLengthOverall(vesselLengthOverall);
        existingVessel.setVesselBeam(vesselBeam);
        existingVessel.setVesselDraft(vesselDraft);
        existingVessel.setVesselCabin(vesselCabin);
        existingVessel.setVesselBerth(vesselBerth);
        existingVessel.setVesselKeelType(vesselKeelType);
        existingVessel.setVesselFuelType(vesselFuelType);
        existingVessel.setEngineQuantity(engineQuantity);
        existingVessel.setVesselDescription(vesselDescription);

        if (newImageKey != null) {
            existingVessel.setImageKey(newImageKey);
        }

        return vesselRepository.save(existingVessel);
    }

    @Override
    public Vessel findById(Long theId) {
        return vesselRepository.findById(theId).orElseThrow(
                () -> new RuntimeException("Can't get vessel by id " + theId));
    }

    @Override
    public List<Vessel> findAll() {
        return vesselRepository.findAll();
    }

    @Override
    public void deleteById(Long theId) {
        vesselRepository.deleteById(theId);
    }

    private String uploadImageToS3(MultipartFile imageFile) {
        String imageKey = UUID.randomUUID().toString();

        try {
            // Upload the image to S3
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imageFile.getContentType());

            amazonS3.putObject(s3BucketName, imageKey, imageFile.getInputStream(), metadata);

        } catch (IOException e) {
            // Handle the exception (e.g., log it or throw a custom exception)
            throw new RuntimeException("Failed to upload image to S3: " + e.getMessage());
        }

        return imageKey;
    }

    private String getImageKeyFromDatabase(Long vesselId) {
        // Replace this with your actual database retrieval logic
        Vessel existingVessel = vesselRepository.findById(vesselId)
                .orElseThrow(() -> new EntityNotFoundException("Vessel not found with ID: " + vesselId));

        return existingVessel.getImageKey();
    }

    private Vessel mapVesselFromRequestParamsWithImageKey(
            boolean featuredVessel, String vesselMake, String vesselModel, BigDecimal vesselPrice,
            int vesselYear, String vesselLocationCountry, String vesselLocationState,
            BigDecimal vesselLengthOverall, BigDecimal vesselBeam, BigDecimal vesselDraft,
            int vesselCabin, int vesselBerth, String vesselKeelType, String vesselFuelType,
            int engineQuantity, String vesselDescription, String imageKey
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
        newVessel.setEngineQuantity(engineQuantity);
        newVessel.setVesselDescription(vesselDescription);
        newVessel.setImageKey(imageKey);

        return newVessel;
    }

}

