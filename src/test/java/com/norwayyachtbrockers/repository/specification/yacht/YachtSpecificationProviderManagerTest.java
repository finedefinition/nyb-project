package com.norwayyachtbrockers.repository.specification.yacht;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

@Order(450)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class YachtSpecificationProviderManagerTest {

    @Mock
    private SpecificationProvider<Yacht> specificationProvider1;

    @Mock
    private SpecificationProvider<Yacht> specificationProvider2;

    private YachtSpecificationProviderManager yachtSpecificationProviderManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(specificationProvider1.getKey()).thenReturn("key1");
        when(specificationProvider2.getKey()).thenReturn("key2");

        List<SpecificationProvider<Yacht>> providers = List.of(specificationProvider1, specificationProvider2);
        yachtSpecificationProviderManager = new YachtSpecificationProviderManager(providers);
    }

    @Test
    @DisplayName("getSpecificationProvider - Successfully retrieves the correct provider")
    @Order(10)
    void testGetSpecificationProvider_Success() {
        SpecificationProvider<Yacht> result = yachtSpecificationProviderManager.getSpecificationProvider("key1");
        assertEquals(specificationProvider1, result);

        result = yachtSpecificationProviderManager.getSpecificationProvider("key2");
        assertEquals(specificationProvider2, result);
    }

    @Test
    @DisplayName("getSpecificationProvider - Throws exception when key is not found")
    @Order(20)
    void testGetSpecificationProvider_NotFound() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                yachtSpecificationProviderManager.getSpecificationProvider("unknownKey")
        );

        assertEquals("Can't find any correct specification provider for the key unknownKey",
                exception.getMessage());
    }
}
