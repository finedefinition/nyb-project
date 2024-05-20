package com.norwayyachtbrockers.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.annotation.DirtiesContext;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Order(720)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CognitoUtilsTest {

    @Test
    @Order(10)
    @DisplayName("generateSecretHash - Should generate correct secret hash")
    void testGenerateSecretHash() {
        // Arrange
        String username = "testUser";
        String clientId = "testClientId";
        String clientSecret = "testClientSecret";

        // Act
        String result = CognitoUtils.generateSecretHash(username, clientId, clientSecret);

        // Assert
        String expectedHash = calculateExpectedHash(username, clientId, clientSecret);
        Assertions.assertEquals(expectedHash, result);
    }

    @Test
    @Order(20)
    @DisplayName("calculateSecretHash - Should calculate correct secret hash")
    void testCalculateSecretHash() {
        // Arrange
        String userPoolClientId = "testPoolClientId";
        String userPoolClientSecret = "testPoolClientSecret";
        String userName = "testUser";

        // Act
        String result = CognitoUtils.calculateSecretHash(userPoolClientId, userPoolClientSecret, userName);

        // Assert
        String expectedHash = calculateExpectedHash(userName, userPoolClientId, userPoolClientSecret);
        Assertions.assertEquals(expectedHash, result);
    }

    private String calculateExpectedHash(String part1, String part2, String secret) {
        try {
            String data = part1 + part2;
            byte[] key = secret.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating expected hash", e);
        }
    }
}