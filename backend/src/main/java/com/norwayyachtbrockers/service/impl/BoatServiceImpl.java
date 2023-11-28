package com.norwayyachtbrockers.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.norwayyachtbrockers.model.Boat;
import com.norwayyachtbrockers.repository.BoatRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.norwayyachtbrockers.service.BoatService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoatServiceImpl implements BoatService {
        private final BoatRepository boatRepository;
        private final AmazonS3 amazonS3;
        private final String s3BucketName;

        @Autowired
        public BoatServiceImpl(BoatRepository boatRepository, AmazonS3 amazonS3, @Value("${s3.bucket.name}") String s3BucketName) {
            this.boatRepository = boatRepository;
            this.amazonS3 = amazonS3;
            this.s3BucketName = s3BucketName;
        }

    @Override
    public Boat save(Boat boat, MultipartFile imageFile) {
        boat.setCreatedAt(LocalDateTime.now());
        // Generate a unique key for the image in S3 (e.g., using UUID)
        String imageKey = UUID.randomUUID().toString();

        try {
            // Upload the image to S3
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imageFile.getContentType());

            amazonS3.putObject(s3BucketName, imageKey, imageFile.getInputStream(), metadata);

            // Set the image key in the boat entity
            boat.setImageKey(imageKey);
        } catch (IOException e) {
            // Handle the exception (e.g., log it or throw a custom exception)
        throw new RuntimeException("Failed to upload image to S3: " + e.getMessage());
    }

        return boatRepository.save(boat);
    }

    @Override
    public Boat update(Long boatId, Boat boat, MultipartFile imageFile) {
        // First, check if the boat with the given ID exists
        Boat existingBoat = boatRepository.findById(boatId)
                .orElseThrow(() -> new EntityNotFoundException("Boat not found with ID: " + boatId));

        // Update the existing boat entity with the new data
        existingBoat.setBoatName(boat.getBoatName());
        existingBoat.setBoatPrice(boat.getBoatPrice());
        existingBoat.setBoatBrand(boat.getBoatBrand());
        existingBoat.setBoatYear(boat.getBoatYear());

        // Save the updated boat entity
        return boatRepository.save(existingBoat);
    }

    @Override
    public Boat findById(Long theId) {
        return boatRepository.findById(theId).orElseThrow(
                () -> new RuntimeException("Can't get boat by id " + theId));
    }

    @Override
    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    @Override
    public void deleteById(Long theId) {
        boatRepository.deleteById(theId);
    }
}
