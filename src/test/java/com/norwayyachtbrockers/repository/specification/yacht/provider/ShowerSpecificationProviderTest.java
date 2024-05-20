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

@Order(410)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ShowerSpecificationProviderTest {

    private ShowerSpecificationProvider showerSpecificationProvider;

    @BeforeEach
    void setUp() {
        showerSpecificationProvider = new ShowerSpecificationProvider();
    }

    @Test
    @DisplayName("getKey - Should return 'shower'")
    void testGetKey() {
        // Act
        String key = showerSpecificationProvider.getKey();

        // Assert
        assertEquals("shower", key);
    }

    @Test
    @DisplayName("getSpecification - Should return Specification<Yacht>")
    void testGetSpecification() {
        // Arrange
        Integer[] param = {1, 2};
        Specification<Yacht> expectedSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("yachtDetail").get("shower"), param[0], param[1]);

        try (MockedStatic<YachtSpecificationUtil> mockedStatic = mockStatic(YachtSpecificationUtil.class)) {
            mockedStatic.when(() -> YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(any(Integer[].class),
                            any(String.class), any(String.class)))
                    .thenReturn(expectedSpecification);

            // Act
            Specification<Yacht> actualSpecification = showerSpecificationProvider.getSpecification(param);

            // Assert
            assertEquals(expectedSpecification, actualSpecification);
            mockedStatic.verify(() -> YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(param, "yachtDetail",
                    "shower"));
        }
    }
}