package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.request.OwnerInfoRequestDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.repository.OwnerInfoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(650)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OwnerInfoServiceImplTest {

    @MockBean
    private OwnerInfoRepository ownerInfoRepository;

    @Autowired
    private OwnerInfoServiceImpl ownerInfoService;

    private OwnerInfoRequestDto requestDto;
    private OwnerInfo existingOwnerInfo;

    private static final Long EXISTING_ID = 1L;
    private static final Long NON_EXISTENT_ID = 999L;

    private static final String OWNER_FIRST_NAME = "John";
    private static final String OWNER_LAST_NAME = "Doe";
    private static final String OWNER_PHONE_NUMBER = "1234567890";
    private static final String OWNER_EMAIL = "john.doe@example.com";
    private static final String EXPECTED_ERROR_MESSAGE = "OwnerInfo with ID: %d not found";

    private static final String UPDATED_OWNER_FIRST_NAME = "Jane";
    private static final String UPDATED_OWNER_LAST_NAME = "Smith";
    private static final String UPDATED_OWNER_PHONE_NUMBER = "0987654321";
    private static final String UPDATED_OWNER_EMAIL = "jane.smith@example.com";

    @BeforeEach
    public void setUp() {
        requestDto = new OwnerInfoRequestDto();
        requestDto.setFirstName(OWNER_FIRST_NAME);
        requestDto.setLastName(OWNER_LAST_NAME);
        requestDto.setPhoneNumber(OWNER_PHONE_NUMBER);
        requestDto.setEmail(OWNER_EMAIL);

        existingOwnerInfo = new OwnerInfo();
        existingOwnerInfo.setId(EXISTING_ID);
        existingOwnerInfo.setFirstName(OWNER_FIRST_NAME);
        existingOwnerInfo.setLastName(OWNER_LAST_NAME);
        existingOwnerInfo.setTelephone(OWNER_PHONE_NUMBER);
        existingOwnerInfo.setEmail(OWNER_EMAIL);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(ownerInfoRepository);
    }

    @Test
    @Order(10)
    @DisplayName("save - Successfully saves OwnerInfo")
    @Transactional
    void testSave_Success() {
        // Arrange
        when(ownerInfoRepository.save(Mockito.any(OwnerInfo.class))).thenAnswer(invocation -> {
            OwnerInfo savedOwnerInfo = invocation.getArgument(0);
            savedOwnerInfo.setId(EXISTING_ID);
            return savedOwnerInfo;
        });

        // Act
        OwnerInfo savedOwnerInfo = ownerInfoService.save(requestDto);

        // Assert
        assertNotNull(savedOwnerInfo, "Saved OwnerInfo should not be null");
        assertEquals(OWNER_FIRST_NAME, savedOwnerInfo.getFirstName(),
                "First name should match the requested first name");
        assertEquals(OWNER_LAST_NAME, savedOwnerInfo.getLastName(),
                "Last name should match the requested last name");
        assertEquals(OWNER_PHONE_NUMBER, savedOwnerInfo.getTelephone(),
                "Phone number should match the requested phone number");
        assertEquals(OWNER_EMAIL, savedOwnerInfo.getEmail(),
                "Email should match the requested email");
    }

    @Test
    @Order(20)
    @DisplayName("findId - Successfully finds OwnerInfo by ID")
    void testFindId_Success() {
        // Arrange
        when(ownerInfoRepository.findById(EXISTING_ID)).thenReturn(Optional.of(existingOwnerInfo));

        // Act
        OwnerInfo foundOwnerInfo = ownerInfoService.findId(EXISTING_ID);

        // Assert
        assertNotNull(foundOwnerInfo, "Found OwnerInfo should not be null");
        assertEquals(EXISTING_ID, foundOwnerInfo.getId(), "Found OwnerInfo ID should match the requested ID");
    }

    @Test
    @Order(30)
    @DisplayName("findAll - Successfully finds all OwnerInfo entities")
    void testFindAll_Success() {
        // Arrange
        when(ownerInfoRepository.findAll()).thenReturn(List.of(existingOwnerInfo));

        // Act
        List<OwnerInfo> ownerInfos = ownerInfoService.findAll();
        ownerInfos.add(existingOwnerInfo);

        // Assert
        assertNotNull(ownerInfos, "List of OwnerInfo should not be null");
        assertFalse(ownerInfos.isEmpty(), "List of OwnerInfo should have at least one entity");
    }

    @Test
    @Order(40)
    @DisplayName("update - Successfully updates OwnerInfo")
    @Transactional
    void testUpdate_Success() {
        // Arrange
        OwnerInfoRequestDto updateDto = new OwnerInfoRequestDto();
        updateDto.setFirstName(UPDATED_OWNER_FIRST_NAME);
        updateDto.setLastName(UPDATED_OWNER_LAST_NAME);
        updateDto.setPhoneNumber(UPDATED_OWNER_PHONE_NUMBER);
        updateDto.setEmail(UPDATED_OWNER_EMAIL);

        // Mocking behavior for update method
        when(ownerInfoRepository.findById(EXISTING_ID)).thenReturn(Optional.of(existingOwnerInfo));

        // Act
        OwnerInfo updatedOwnerInfo = ownerInfoService.update(updateDto, EXISTING_ID);

        // Assert
        assertNotNull(updatedOwnerInfo, "Updated OwnerInfo should not be null");
        assertEquals(UPDATED_OWNER_FIRST_NAME, updatedOwnerInfo.getFirstName(),
         "Updated first name should match the requested updated first name");
        assertEquals(UPDATED_OWNER_LAST_NAME, updatedOwnerInfo.getLastName(),
         "Updated last name should match the requested updated last name");
        assertEquals(UPDATED_OWNER_PHONE_NUMBER, updatedOwnerInfo.getTelephone(),
         "Updated phone number should match the requested updated phone number");
        assertEquals(UPDATED_OWNER_EMAIL, updatedOwnerInfo.getEmail(),
         "Updated email should match the requested updated email");
    }

    @Test
    @Order(50)
    @DisplayName("deleteById - Successfully deletes OwnerInfo by ID")
    @Transactional
    void testDeleteById_Success() {
        // Arrange
        when(ownerInfoRepository.findById(EXISTING_ID)).thenReturn(Optional.of(existingOwnerInfo));

        // Act & Assert
        assertDoesNotThrow(() -> ownerInfoService.deleteById(EXISTING_ID),
         "Deleting by ID should not throw an exception");
        verify(ownerInfoRepository, times(1)).delete(existingOwnerInfo);
    }

    @Test
    @Order(60)
    @DisplayName("findId - Throws exception when OwnerInfo not found")
    void testFindId_NotFound() {
        // Arrange
        when(ownerInfoRepository.findById(NON_EXISTENT_ID))
                .thenThrow(new AppEntityNotFoundException(String.format(EXPECTED_ERROR_MESSAGE,
                        NON_EXISTENT_ID)));

        // Act & Assert
        Exception exception = assertThrows(AppEntityNotFoundException.class,
                () -> ownerInfoService.findId(NON_EXISTENT_ID),
                String.format(EXPECTED_ERROR_MESSAGE,
                        NON_EXISTENT_ID));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(String.format(EXPECTED_ERROR_MESSAGE,
                        NON_EXISTENT_ID)),
                "Exception message should contain EXPECTED_ERROR_MESSAGE'");
    }
}
