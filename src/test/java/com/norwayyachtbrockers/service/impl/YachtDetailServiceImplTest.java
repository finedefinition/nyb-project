package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.request.YachtDetailRequestDto;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.repository.YachtDetailRepository;
import com.norwayyachtbrockers.repository.projections.YachtDetailProjection;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(680)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class YachtDetailServiceImplTest {

    @MockBean
    private YachtDetailRepository yachtDetailRepository;

    @Autowired
    private YachtDetailServiceImpl yachtDetailService;

    private YachtDetail yachtDetail;
    private YachtDetailRequestDto yachtDetailRequestDto;

    private static final Long YACHT_DETAIL_ID = 1L;
    private static final String DESCRIPTION = "YachtDescription";
    private static final String DTO_DESCRIPTION = "YachtDescription";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        yachtDetail = new YachtDetail();
        yachtDetail.setId(YACHT_DETAIL_ID);
        yachtDetail.setDescription(DESCRIPTION);

        yachtDetailRequestDto = new YachtDetailRequestDto();
        yachtDetailRequestDto.setDescription(DTO_DESCRIPTION);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(yachtDetailRepository);
    }

    @Test
    @Order(10)
    @DisplayName("saveYachtDetail - Successfully saves a yacht detail from DTO")
    @Transactional
    void testSaveYachtDetail_Success() {
        // Arrange
        when(yachtDetailRepository.save(Mockito.any(YachtDetail.class))).thenAnswer(invocation -> {
            YachtDetail yachtDetail = invocation.getArgument(0);
            yachtDetail.setId(YACHT_DETAIL_ID);
            return yachtDetail;
        });

        // Act
        YachtDetail savedYachtDetail = yachtDetailService.save(yachtDetailRequestDto);

        // Assert
        assertNotNull(savedYachtDetail, "Saved yacht detail should not be null");
        assertEquals(YACHT_DETAIL_ID, savedYachtDetail.getId(), "Yacht detail ID should match");

        ArgumentCaptor<YachtDetail> captor = ArgumentCaptor.forClass(YachtDetail.class);
        verify(yachtDetailRepository, times(1)).save(captor.capture());

        YachtDetail capturedYachtDetail = captor.getValue();
        assertEquals(DESCRIPTION, capturedYachtDetail.getDescription(), "Yacht detail description should match");
    }

    @Test
    @Order(20)
    @DisplayName("findId - Successfully finds a yacht detail by ID")
    void testFindId_Success() {
        // Arrange
        when(yachtDetailRepository.findById(YACHT_DETAIL_ID)).thenReturn(Optional.of(yachtDetail));

        // Act
        YachtDetail foundYachtDetail = yachtDetailService.findId(YACHT_DETAIL_ID);

        // Assert
        assertNotNull(foundYachtDetail, "Found yacht detail should not be null");
        assertEquals(YACHT_DETAIL_ID, foundYachtDetail.getId(), "Yacht detail ID should match");
        assertEquals(DESCRIPTION, foundYachtDetail.getDescription(), "Yacht detail description should match");
    }

    @Test
    @Order(30)
    @DisplayName("findAll - Successfully retrieves all yacht details sorted by ID")
    void testFindAll_Success() {
        // Arrange
        YachtDetail anotherYachtDetail = new YachtDetail();
        anotherYachtDetail.setId(2L);
        anotherYachtDetail.setDescription("AnotherDescription");

        List<YachtDetail> yachtDetails = Arrays.asList(yachtDetail, anotherYachtDetail);
        when(yachtDetailRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(yachtDetails);

        // Act
        List<YachtDetailProjection> foundYachtDetails = yachtDetailService.findAll();

        // Assert
        assertNotNull(foundYachtDetails,
                "Found yacht details list should not be null");
        assertEquals(2, foundYachtDetails.size(),
                "Found yacht details list size should be 2");
        assertEquals(YACHT_DETAIL_ID, foundYachtDetails.get(0).getId(),
                "First yacht detail ID should match");
        assertEquals("AnotherDescription", foundYachtDetails.get(1).getDescription(),
                "Second yacht detail name should match");
    }

    @Test
    @Order(40)
    @DisplayName("updateYachtDetail - Successfully updates a yacht detail from DTO")
    @Transactional
    void testUpdateYachtDetail_Success() {
        // Arrange
        when(yachtDetailRepository.findById(YACHT_DETAIL_ID)).thenReturn(Optional.of(yachtDetail));
        // doNothing().when(yachtDetailMapper).updateYachtDetailFromDto(yachtDetail, yachtDetailRequestDto);
        when(yachtDetailRepository.save(yachtDetail)).thenReturn(yachtDetail);

        // Act
        YachtDetail updatedYachtDetail = yachtDetailService.update(yachtDetailRequestDto, YACHT_DETAIL_ID);

        // Assert
        assertNotNull(updatedYachtDetail,
                "Updated yacht detail should not be null");
        assertEquals(YACHT_DETAIL_ID, updatedYachtDetail.getId(),
                "Yacht detail ID should match");
        assertEquals(DESCRIPTION, updatedYachtDetail.getDescription(),
                "Yacht detail name should match");

        verify(yachtDetailRepository, times(1)).save(yachtDetail);
    }

    @Test
    @Order(50)
    @DisplayName("deleteById - Successfully deletes a yacht detail by ID")
    @Transactional
    void testDeleteById_Success() {
        // Arrange
        when(yachtDetailRepository.findById(YACHT_DETAIL_ID)).thenReturn(Optional.of(yachtDetail));
        doNothing().when(yachtDetailRepository).delete(yachtDetail);

        // Act
        yachtDetailService.deleteById(YACHT_DETAIL_ID);

        // Assert
        verify(yachtDetailRepository, times(1)).delete(yachtDetail);
    }
}
