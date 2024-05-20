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

@Order(420)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TownSpecificationProviderTest {

    private TownSpecificationProvider townSpecificationProvider;

    @BeforeEach
    void setUp() {
        townSpecificationProvider = new TownSpecificationProvider();
    }

    @Test
    @DisplayName("getKey - Should return 'town'")
    void testGetKey() {
        // Act
        String key = townSpecificationProvider.getKey();

        // Assert
        assertEquals("town", key);
    }

    @Test
    @DisplayName("getSpecification - Should return Specification<Yacht>")
    void testGetSpecification() {
        // Arrange
        String param = "Oslo";
        Specification<Yacht> expectedSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), param);

        try (MockedStatic<YachtSpecificationUtil> mockedStatic = mockStatic(YachtSpecificationUtil.class)) {
            mockedStatic.when(() -> YachtSpecificationUtil.getSpecificationOrElseThrow(any(String.class),
                            any(String.class), any(String.class)))
                    .thenReturn(expectedSpecification);

            // Act
            Specification<Yacht> actualSpecification = townSpecificationProvider.getSpecification(param);

            // Assert
            assertEquals(expectedSpecification, actualSpecification);
            mockedStatic.verify(() -> YachtSpecificationUtil.getSpecificationOrElseThrow(param, "town",
                    "name"));
        }
    }
}