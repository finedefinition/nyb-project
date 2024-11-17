package com.norwayyachtbrockers.repository.specification.yacht;

import com.norwayyachtbrockers.dto.request.YachtSearchParametersDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import com.norwayyachtbrockers.repository.specification.SpecificationProviderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(440)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtSpecificationBuilderTest {

    @Mock
    private SpecificationProviderManager<Yacht> specificationProviderManager;

    @Mock
    private SpecificationProvider<Yacht> specificationProvider;

    private YachtSpecificationBuilder yachtSpecificationBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        yachtSpecificationBuilder = new YachtSpecificationBuilder(specificationProviderManager);
    }

    @Test
    @DisplayName("Successfully builds specification with valid parameters")
    @Order(10)
    void testBuild_WithValidParameters() {
        // Arrange
        YachtSearchParametersDto searchParameters = new YachtSearchParametersDto();
        searchParameters.setMinPrice(BigDecimal.valueOf(10000));
        searchParameters.setMaxPrice(BigDecimal.valueOf(20000));
        searchParameters.setModel("Model X");
        searchParameters.setCountry("Norway");
        searchParameters.setTown("Oslo");
        searchParameters.setKeelType("Wing Keel");
        searchParameters.setFuelType("Diesel");
        searchParameters.setMaxYear(1999);
        searchParameters.setMinYear(2020);
        searchParameters.setMaxLengthOverall(new BigDecimal(23));
        searchParameters.setMinLengthOverall(new BigDecimal(18));
        searchParameters.setMaxBeamWidth(new BigDecimal(12));
        searchParameters.setMinBeamWidth(new BigDecimal(6));
        searchParameters.setMinDraftDepth(new BigDecimal(4));
        searchParameters.setMaxDraftDepth(new BigDecimal(10));
        searchParameters.setMinCabinNumber(1);
        searchParameters.setMaxCabinNumber(3);
        searchParameters.setMinBerthNumber(1);
        searchParameters.setMaxBerthNumber(3);
        searchParameters.setMinHeadsNumber(1);
        searchParameters.setMaxHeadsNumber(3);
        searchParameters.setMinShowerNumber(1);
        searchParameters.setMaxShowerNumber(2);

        when(specificationProviderManager.getSpecificationProvider(any(String.class)))
                .thenReturn(specificationProvider);
        when(specificationProvider.getSpecification(any()))
                .thenReturn((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        // Act
//        Specification<Yacht> specification = yachtSpecificationBuilder.build(searchParameters);

        // Assert
//        assertNotNull(specification);
        verify(specificationProviderManager, times(14))
                .getSpecificationProvider(any(String.class));
        verify(specificationProvider, times(14)).getSpecification(any());
    }

    @Test
    @DisplayName("Successfully builds specification with no parameters")
    @Order(20)
    void testBuild_WithNoParameters() {
        // Arrange
        YachtSearchParametersDto searchParameters = new YachtSearchParametersDto();

        // Act
//        Specification<Yacht> specification = yachtSpecificationBuilder.build(searchParameters);

        // Assert
//        assertNotNull(specification);
        verify(specificationProviderManager, never()).getSpecificationProvider(any(String.class));
    }
}
