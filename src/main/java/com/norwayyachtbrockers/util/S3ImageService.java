package com.norwayyachtbrockers.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ImageService {

    private final AmazonS3 amazonS3;
    private final String s3BucketName;

    @Autowired
    public S3ImageService(AmazonS3 amazonS3, @Value("${s3.bucket.name}") String s3BucketName) {
        this.amazonS3 = amazonS3;
        this.s3BucketName = s3BucketName;
    }


    public String uploadImageToS3(MultipartFile imageFile) {
        String imageKey = UUID.randomUUID().toString();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imageFile.getContentType());
            amazonS3.putObject(s3BucketName, imageKey, imageFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to S3: " + e.getMessage(), e);
        }
        return imageKey;
    }

}