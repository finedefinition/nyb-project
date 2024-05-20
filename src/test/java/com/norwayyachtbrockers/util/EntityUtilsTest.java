package com.norwayyachtbrockers.util;

import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@Order(730)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EntityUtilsTest {

    @Mock
    private JpaRepository<TestEntity, Long> repository;

    private static final Long ENTITY_ID = 1L;
    private static final String ENTITY_NAME = "TestEntity";

    private TestEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entity = new TestEntity();
        entity.setId(ENTITY_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(repository);
    }

    @Test
    @Order(10)
    @DisplayName("findEntityOrThrow - Successfully finds entity by ID")
    void testFindEntityOrThrow_Success() {
        // Arrange
        when(repository.findById(ENTITY_ID)).thenReturn(Optional.of(entity));

        // Act
        TestEntity foundEntity = EntityUtils.findEntityOrThrow(ENTITY_ID, repository, ENTITY_NAME);

        // Assert
        assertNotNull(foundEntity, "Found entity should not be null");
        assertEquals(ENTITY_ID, foundEntity.getId(), "Entity ID should match");
    }

    @Test
    @Order(20)
    @DisplayName("findEntityOrThrow - Throws exception when entity not found")
    void testFindEntityOrThrow_EntityNotFound() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        AppEntityNotFoundException exception = assertThrows(
                AppEntityNotFoundException.class,
                () -> EntityUtils.findEntityOrThrow(ENTITY_ID, repository, ENTITY_NAME),
                "Expected findEntityOrThrow to throw, but it didn't"
        );

        assertTrue(exception.getMessage().contains(ENTITY_NAME), "Exception message should contain entity name");
        assertTrue(exception.getMessage().contains(ENTITY_ID.toString()), "Exception message should contain entity ID");
    }

    // Mock entity class for testing purposes
    static class TestEntity {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}