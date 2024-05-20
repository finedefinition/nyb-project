package com.norwayyachtbrockers.repository.specification.yacht.provider;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.util.YachtSpecificationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockedStatic;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@Order(380)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class LengthOverallSpecificationProviderTest {

    private LengthOverallSpecificationProvider lengthOverallSpecificationProvider;

    @BeforeEach
    void setUp() {
        lengthOverallSpecificationProvider = new LengthOverallSpecificationProvider();
    }

    @Test
    @DisplayName("getKey - Should return 'lengthOverall'")
    void testGetKey() {
        // Act
        String key = lengthOverallSpecificationProvider.getKey();

        // Assert
        assertEquals("lengthOverall", key);
    }

    @Test
    @DisplayName("getSpecification - Should return Specification<Yacht>")
    void testGetSpecification() {
        // Arrange
        BigDecimal[] param = {new BigDecimal("10.5"), new BigDecimal("20.5")};
        Specification<Yacht> expectedSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("yachtModel").get("lengthOverall"), param[0], param[1]);

        try (MockedStatic<YachtSpecificationUtil> mockedStatic = mockStatic(YachtSpecificationUtil.class)) {
            mockedStatic.when(() -> YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(any(BigDecimal[].class),
                            any(String.class), any(String.class)))
                    .thenReturn(expectedSpecification);

            // Act
            Specification<Yacht> actualSpecification = lengthOverallSpecificationProvider.getSpecification(param);

            // Assert
            assertEquals(expectedSpecification, actualSpecification);
            mockedStatic.verify(() -> YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(param, "yachtModel",
                    "lengthOverall"));
        }
    }
}