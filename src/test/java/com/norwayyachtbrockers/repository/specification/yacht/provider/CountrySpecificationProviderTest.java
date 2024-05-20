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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@Order(330)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CountrySpecificationProviderTest {

    private CountrySpecificationProvider countrySpecificationProvider;

    @BeforeEach
    void setUp() {
        countrySpecificationProvider = new CountrySpecificationProvider();
    }

    @Test
    @DisplayName("getKey - Should return 'country'")
    void testGetKey() {
        // Act
        String key = countrySpecificationProvider.getKey();

        // Assert
        assertEquals("country", key);
    }

    @Test
    @DisplayName("getSpecification - Should return Specification<Yacht>")
    void testGetSpecification() {
        // Arrange
        String param = "Norway";
        Specification<Yacht> expectedSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("country"), param);

        try (MockedStatic<YachtSpecificationUtil> mockedStatic = mockStatic(YachtSpecificationUtil.class)) {
            mockedStatic.when(() -> YachtSpecificationUtil.getSpecificationOrElseThrow(any(String.class),
                            any(String.class), any(String.class)))
                    .thenReturn(expectedSpecification);

            // Act
            Specification<Yacht> actualSpecification = countrySpecificationProvider.getSpecification(param);

            // Assert
            assertEquals(expectedSpecification, actualSpecification);
            mockedStatic.verify(() -> YachtSpecificationUtil.getSpecificationOrElseThrow(param, "country",
                    "name"));
        }
    }
}