package com.norwayyachtbrockers.audit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Order(20)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuditAwareImplTest {

    @Autowired
    private AuditAwareImpl auditAware;

    private static final String EXPECTED_AUDITOR = "SERGII BEZRUKOV";

    @Test
    @Order(10)
    @DisplayName("getCurrentAuditor - Successfully returns the current auditor")
    void testGetCurrentAuditor_Success() {
        // Act
        Optional<String> currentAuditor = auditAware.getCurrentAuditor();

        // Assert
        assertTrue(currentAuditor.isPresent(), "Current auditor should be present");
        assertEquals(EXPECTED_AUDITOR, currentAuditor.get(), "Auditor name should match the expected value");
    }
}