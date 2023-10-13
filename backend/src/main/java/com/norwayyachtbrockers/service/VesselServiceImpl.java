package com.norwayyachtbrockers.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.repository.VesselRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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
    public Vessel update(Long vesselId, Vessel vessel, MultipartFile imageFile) {

        Vessel existingVessel = vesselRepository.findById(vesselId)
                .orElseThrow(() -> new EntityNotFoundException("Vessel not found with ID: " + vesselId));

//        existingVessel.setFeaturedVessel(vessel.isFeaturedVessel());
        existingVessel.setVesselMake(vessel.getVesselMake());
        existingVessel.setVesselModel(vessel.getVesselModel());
        existingVessel.setVesselPrice(vessel.getVesselPrice());
        existingVessel.setVesselYear(vessel.getVesselYear());
        existingVessel.setVesselLocationCountry(vessel.getVesselLocationCountry());
        existingVessel.setVesselLocationState(vessel.getVesselLocationState());
        existingVessel.setVesselLengthOverall(vessel.getVesselLengthOverall());
        existingVessel.setVesselBeam(vessel.getVesselBeam());
        existingVessel.setVesselDraft(vessel.getVesselDraft());
        existingVessel.setVesselCabin(vessel.getVesselCabin());
        existingVessel.setVesselBerth(vessel.getVesselBerth());
        existingVessel.setVesselKeelType(vessel.getVesselKeelType());
        existingVessel.setVesselFuelType(vessel.getVesselFuelType());
        existingVessel.setEngineQuantity(vessel.getEngineQuantity());
        existingVessel.setVesselDescription(vessel.getVesselDescription());
        existingVessel.setCreatedAt(vessel.getCreatedAt());
        existingVessel.setImageKey(vessel.getImageKey());

        // Save the updated boat entity
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
}
