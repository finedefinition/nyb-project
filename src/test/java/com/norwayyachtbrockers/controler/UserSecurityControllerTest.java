package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.config.TestSecurityConfig;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.enums.UserRoles;
import com.norwayyachtbrockers.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Order(80)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ContextConfiguration(classes = {UserController.class, TestSecurityConfig.class})
class UserSecurityControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User user;
    private UserResponseDto userResponseDto;

    private static final String BASE_URL = "/users";
    private static final Long USER_ID = 1L;
    private static final String COGNITO_SUB = "some-cognito-sub-id";
    private static final Long YACHT_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Lennon";
    private static final String EMAIL = "john.lennon@gmail.com";
    private static final UserRoles USER_ROLES = UserRoles.ROLE_USER;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setCognitoSub(COGNITO_SUB);
        user.setUserRoles(USER_ROLES);

        userResponseDto = new UserResponseDto();
        userResponseDto.setId(USER_ID);
        userResponseDto.setEmail(EMAIL);
        userResponseDto.setFirstName(FIRST_NAME);
        userResponseDto.setLastName(LAST_NAME);
        userResponseDto.setCognitoSub(COGNITO_SUB);
        userResponseDto.setRoleName(USER_ROLES.toString());
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(userService);
    }


    @Test
    @Order(10)
    @DisplayName("addFavouriteYacht - Successfully adds a favourite yacht")
    void testAddFavouriteYacht_Success() throws Exception {
        // Arrange
        doNothing().when(userService).addFavouriteYachtToUser(COGNITO_SUB, YACHT_ID);

        // Act & Assert
        mockMvc.perform(post(BASE_URL + "/" + COGNITO_SUB + "/favouriteYachts/" + YACHT_ID))
                .andExpect(status().isCreated())
                .andReturn();

        verify(userService, times(1)).addFavouriteYachtToUser(COGNITO_SUB, YACHT_ID);
    }

    @Test
    @Order(20)
    @DisplayName("deleteFavouriteYacht - Successfully deletes a favourite yacht")
    void testDeleteFavouriteYacht_Success() throws Exception {
        // Arrange
        doNothing().when(userService).removeFavouriteYacht(COGNITO_SUB, YACHT_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + COGNITO_SUB + "/favouriteYachts/" + YACHT_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(userService, times(1)).removeFavouriteYacht(COGNITO_SUB, YACHT_ID);
    }

    @Test
    @Order(30)
    @DisplayName("deleteFavouriteYacht - Handles yacht not found")
    void testDeleteFavouriteYacht_NotFound() throws Exception {
        // Arrange
        doThrow(new AppEntityNotFoundException("Yacht not found"))
                .when(userService).removeFavouriteYacht(COGNITO_SUB, YACHT_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + COGNITO_SUB + "/favouriteYachts/" + YACHT_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Yacht not found"))
                .andReturn();

        verify(userService, times(1)).removeFavouriteYacht(COGNITO_SUB, YACHT_ID);
    }
}