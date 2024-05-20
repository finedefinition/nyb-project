package com.norwayyachtbrockers.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(740)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class S3ImageServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private S3ImageService s3ImageService;

    @Value("${s3.bucket.name}")
    private String s3BucketName = "test-bucket";

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Captor
    private ArgumentCaptor<InputStream> inputStreamCaptor;

    @Captor
    private ArgumentCaptor<ObjectMetadata> metadataCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        s3ImageService = new S3ImageService(amazonS3, s3BucketName);
    }

    @Test
    @DisplayName("uploadImageToS3 - Should upload image and return image key")
    void testUploadImageToS3() throws IOException {
        // Arrange
        String contentType = "image/jpeg";
        byte[] content = new byte[]{1, 2, 3};
        InputStream inputStream = new ByteArrayInputStream(content);

        when(multipartFile.getContentType()).thenReturn(contentType);
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        // Act
        String result = s3ImageService.uploadImageToS3(multipartFile);

        // Assert
        verify(amazonS3, times(1)).putObject(eq(s3BucketName), stringCaptor.capture(),
                inputStreamCaptor.capture(), metadataCaptor.capture());

        assertEquals(contentType, metadataCaptor.getValue().getContentType());
        assertEquals(inputStream, inputStreamCaptor.getValue());
        assertEquals(UUID.fromString(result).toString(), result);
    }

    @Test
    @DisplayName("uploadImageToS3 - Should throw RuntimeException on IOException")
    void testUploadImageToS3ThrowsException() throws IOException {
        // Arrange
        when(multipartFile.getInputStream()).thenThrow(new IOException("Test Exception"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            s3ImageService.uploadImageToS3(multipartFile);
        });

        assertEquals("Failed to upload image to S3: Test Exception", exception.getMessage());
    }
}
