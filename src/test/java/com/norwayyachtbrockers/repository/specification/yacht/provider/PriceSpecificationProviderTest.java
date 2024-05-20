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

@Order(400)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PriceSpecificationProviderTest {

    private PriceSpecificationProvider priceSpecificationProvider;

    @BeforeEach
    void setUp() {
        priceSpecificationProvider = new PriceSpecificationProvider();
    }

    @Test
    @DisplayName("getKey - Should return 'price'")
    void testGetKey() {
        // Act
        String key = priceSpecificationProvider.getKey();

        // Assert
        assertEquals("price", key);
    }

    @Test
    @DisplayName("getSpecification - Should return Specification<Yacht>")
    void testGetSpecification() {
        // Arrange
        BigDecimal[] param = {BigDecimal.valueOf(1000000), BigDecimal.valueOf(5000000)};
        Specification<Yacht> expectedSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), param[0], param[1]);

        try (MockedStatic<YachtSpecificationUtil> mockedStatic = mockStatic(YachtSpecificationUtil.class)) {
            mockedStatic.when(() -> YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(any(BigDecimal[].class),
                            any(String.class)))
                    .thenReturn(expectedSpecification);

            // Act
            Specification<Yacht> actualSpecification = priceSpecificationProvider.getSpecification(param);

            // Assert
            assertEquals(expectedSpecification, actualSpecification);
            mockedStatic.verify(() -> YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(param, "price"));
        }
    }
}