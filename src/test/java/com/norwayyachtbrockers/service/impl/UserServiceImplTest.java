package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.dto.mapper.UserMapper;
import com.norwayyachtbrockers.dto.response.UserFavouriteYachtsResponseDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.UserRepository;
import com.norwayyachtbrockers.repository.YachtRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Order(670)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private YachtRepository yachtRepository;
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userService;

    private User user;
    private Yacht yacht;

    private static final Long USER_ID = 1L;
    private static final Long YACHT_ID = 1L;
    private static final String USER_EMAIL = "test@example.com";
    private static final String COGNITO_SUB = "cognitoSub";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);

        yacht = new Yacht();
        yacht.setId(YACHT_ID);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(userRepository, userMapper, yachtRepository);
    }

    @Test
    @Order(10)
    @DisplayName("saveUser - Successfully saves a user")
    void testSaveUser_Success() {
        // Arrange
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.saveUser(user);

        // Assert
        assertNotNull(savedUser, "Saved user should not be null");
        assertEquals(USER_ID, savedUser.getId(), "User ID should match");
        assertEquals(USER_EMAIL, savedUser.getEmail(), "User email should match");

        verify(userRepository, times(1)).save(user);
    }

    @Test
    @Order(20)
    @DisplayName("findId - Successfully finds a user by ID")
    void testFindId_Success() {
        // Arrange
        when(userRepository.findByIdAndFetchYachtsEagerly(USER_ID)).thenReturn(Optional.of(user));
        UserResponseDto userResponseDto = new UserResponseDto();
        when(userMapper.convertUserToDto(user)).thenReturn(userResponseDto);

        // Act
        UserResponseDto foundUser = userService.findId(USER_ID);

        // Assert
        assertNotNull(foundUser, "Found user should not be null");
        verify(userRepository, times(1)).findByIdAndFetchYachtsEagerly(USER_ID);
    }

    @Test
    @Order(30)
    @DisplayName("findUserIdByEmail - Successfully finds a user ID by email")
    void testFindUserIdByEmail_Success() {
        // Arrange
        when(userRepository.findUserIdByEmail(USER_EMAIL)).thenReturn(USER_ID);

        // Act
        Long foundUserId = userService.findUserIdByEmail(USER_EMAIL);

        // Assert
        assertEquals(USER_ID, foundUserId, "User ID should match");
        verify(userRepository, times(1)).findUserIdByEmail(USER_EMAIL);
    }

    @Test
    @Order(40)
    @DisplayName("findAll - Successfully retrieves all users sorted by ID")
    void testFindAll_Success() {
        // Arrange
        User anotherUser = new User();
        anotherUser.setId(2L);
        List<User> users = Arrays.asList(user, anotherUser);
        when(userRepository.findAllAndFetchYachtsEagerly()).thenReturn(users);
        UserResponseDto userResponseDto = new UserResponseDto();
        when(userMapper.convertUserToDto(any(User.class))).thenReturn(userResponseDto);

        // Act
        List<UserResponseDto> foundUsers = userService.findAll();

        // Assert
        assertNotNull(foundUsers, "Found users list should not be null");
        assertEquals(2, foundUsers.size(), "Found users list size should be 2");
        verify(userRepository, times(1)).findAllAndFetchYachtsEagerly();
    }

    @Test
    @Order(50)
    @DisplayName("updateUser - Successfully updates a user")
    void testUpdateUser_Success() {
        // Arrange
        when(userRepository.findByIdAndFetchYachtsEagerly(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User updatedUser = userService.updateUser(user, USER_ID);

        // Assert
        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(USER_ID, updatedUser.getId(), "User ID should match");
        assertEquals(USER_EMAIL, updatedUser.getEmail(), "User email should match");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes a user by ID")
    void testDeleteById_Success() {
        // Arrange
        when(userRepository.findByIdAndFetchYachtsEagerly(USER_ID)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        // Act
        userService.deleteById(USER_ID);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @Order(70)
    @DisplayName("addFavouriteYachtToUser - Successfully adds a favourite yacht to user")
    void testAddFavouriteYachtToUser_Success() {
        // Arrange
        when(userRepository.findByCognitoSub(COGNITO_SUB)).thenReturn(Optional.of(user));
        when(yachtRepository.findById(YACHT_ID)).thenReturn(Optional.of(yacht));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        userService.addFavouriteYachtToUser(COGNITO_SUB, YACHT_ID);

        // Assert
        assertTrue(user.getFavouriteYachts().contains(yacht), "Yacht should be in user's favourites");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @Order(80)
    @DisplayName("getFavouriteYachts - Successfully retrieves user's favourite yachts")
    void testGetFavouriteYachts_Success() {
        // Arrange
        user.getFavouriteYachts().add(yacht);
        when(userRepository.findByCognitoSub(COGNITO_SUB)).thenReturn(Optional.of(user));

        // Act
        UserFavouriteYachtsResponseDto favouriteYachts = userService.getFavouriteYachts(COGNITO_SUB);

        // Assert
        assertNotNull(favouriteYachts, "Favourite yachts DTO should not be null");
        assertEquals(1, favouriteYachts.getCount(), "Favourite yachts count should be 1");
        assertTrue(favouriteYachts.getFavouriteYachtIds().contains(YACHT_ID),
                "Yacht ID should be in favourites");
    }

    @Test
    @Order(90)
    @DisplayName("removeFavouriteYacht - Successfully removes a favourite yacht from user")
    void testRemoveFavouriteYacht_Success() {
        // Arrange
        user.getFavouriteYachts().add(yacht);
        when(userRepository.findByCognitoSub(COGNITO_SUB)).thenReturn(Optional.of(user));
        when(yachtRepository.findById(YACHT_ID)).thenReturn(Optional.of(yacht));

        // Act
        userService.removeFavouriteYacht(COGNITO_SUB, YACHT_ID);

        // Assert
        assertFalse(user.getFavouriteYachts().contains(yacht),
                "Yacht should be removed from user's favourites");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @Order(100)
    @DisplayName("removeFavouriteYacht - Throws exception when yacht not in user's favourites")
    void testRemoveFavouriteYacht_NotInFavourites() {
        // Arrange
        when(userRepository.findByCognitoSub(COGNITO_SUB)).thenReturn(Optional.of(user));
        when(yachtRepository.findById(YACHT_ID)).thenReturn(Optional.of(yacht));

        // Act & Assert
        AppEntityNotFoundException exception = assertThrows(
                AppEntityNotFoundException.class,
                () -> userService.removeFavouriteYacht(COGNITO_SUB, YACHT_ID),
                "Expected removeFavouriteYacht to throw, but it didn't"
        );

        assertEquals("Yacht not found in user's favourites", exception.getMessage(),
                "Exception message should match");
    }
}