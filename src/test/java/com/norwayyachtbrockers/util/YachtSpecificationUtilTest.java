package com.norwayyachtbrockers.util;

import com.norwayyachtbrockers.model.Yacht;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Order(760)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtSpecificationUtilTest {

    @Test
    @Order(10)
    @DisplayName("getSpecificationOrElseThrow - Should return Specification<Yacht> when value is provided")
    void testGetSpecificationOrElseThrow_WithValidValue() {
        // Arrange
        String key = "model";
        String keyParam = "name";
        String value = "YachtModel";
        Specification<Yacht> expectedSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(key).get(keyParam), value);

        // Act
        Specification<Yacht> actualSpecification = YachtSpecificationUtil.getSpecificationOrElseThrow(value, key, keyParam);

        // Assert
        assertNotNull(actualSpecification);
    }

    @Test
    @Order(20)
    @DisplayName("getSpecificationOrElseThrow - Should throw IllegalArgumentException when value is null")
    void testGetSpecificationOrElseThrow_WithNullValue() {
        // Arrange
        String key = "model";
        String keyParam = "name";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            YachtSpecificationUtil.getSpecificationOrElseThrow(null, key, keyParam);
        });
    }

    @Test
    @Order(30)
    @DisplayName("getSpecificationInRangeOrElseThrow - Should return Specification<Yacht> when range is provided")
    void testGetSpecificationInRangeOrElseThrow_WithValidRange() {
        // Arrange
        String key = "price";
        String keyParam = "amount";
        Integer[] range = {100000, 500000};
        Specification<Yacht> expectedSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(key).get(keyParam), range[0], range[1]);

        // Act
        Specification<Yacht> actualSpecification = YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(range, key, keyParam);

        // Assert
        assertNotNull(actualSpecification);
    }

    @Test
    @Order(40)
    @DisplayName("getSpecificationInRangeOrElseThrow - Should throw IllegalArgumentException when range is invalid")
    void testGetSpecificationInRangeOrElseThrow_WithInvalidRange() {
        // Arrange
        String key = "price";
        String keyParam = "amount";
        Integer[] invalidRange = {100000}; // Only one value instead of two

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            YachtSpecificationUtil.getSpecificationInRangeOrElseThrow(invalidRange, key, keyParam);
        });
    }
}