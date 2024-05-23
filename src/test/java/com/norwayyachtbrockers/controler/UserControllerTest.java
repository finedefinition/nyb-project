package com.norwayyachtbrockers.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Order(100)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User user;
    private UserResponseDto userResponseDto;

    private static final String BASE_URL = "/users";
    private static final Long USER_ID = 1L;
    private static final String COGNITO_SUB = "some-cognito-sub-id";
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
    @DisplayName("createUser - Successfully creates a user")
    void testCreateUser_Success() throws Exception {
        // Arrange
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andReturn();

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    @Order(20)
    @DisplayName("getUserById - Successfully retrieves a user by ID")
    void testGetUserById_Success() throws Exception {
        // Arrange
        when(userService.findId(USER_ID)).thenReturn(userResponseDto);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(USER_ID))
                .andExpect(jsonPath("$.user_first_name").value(FIRST_NAME))
                .andExpect(jsonPath("$.user_last_name").value(LAST_NAME))
                .andExpect(jsonPath("$.user_email").value(EMAIL))
                .andReturn();

        verify(userService, times(1)).findId(USER_ID);
    }

    @Test
    @Order(30)
    @DisplayName("getAllUsers - Successfully retrieves all users")
    void testGetAllUsers_Success() throws Exception {
        // Arrange
        UserResponseDto anotherUser = new UserResponseDto();
        anotherUser.setId(2L);
        anotherUser.setEmail("test@email.com");
        anotherUser.setFirstName(FIRST_NAME);
        anotherUser.setLastName(LAST_NAME);
        anotherUser.setCognitoSub(COGNITO_SUB);
        anotherUser.setRoleName(USER_ROLES.toString());

        List<UserResponseDto> users = Arrays.asList(userResponseDto, anotherUser);
        when(userService.findAll()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].user_id").value(USER_ID))
                .andExpect(jsonPath("$[0].user_email").value(EMAIL))
                .andExpect(jsonPath("$[1].user_id").value(2L))
                .andExpect(jsonPath("$[1].user_email").value("test@email.com"))
                .andReturn();

        verify(userService, times(1)).findAll();
    }

    @Test
    @Order(40)
    @DisplayName("getAllUsers - Returns NO_CONTENT when no users are found")
    void testGetAllUsers_NoContent() throws Exception {
        // Arrange
        when(userService.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(userService, times(1)).findAll();
    }

    @Test
    @Order(50)
    @DisplayName("updateUser - Successfully updates a user")
    void testUpdateUser_Success() throws Exception {
        // Arrange
        when(userService.updateUser(any(User.class), eq(USER_ID))).thenReturn(user);

        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andReturn();

        verify(userService, times(1)).updateUser(any(User.class), eq(USER_ID));
    }

    @Test
    @Order(60)
    @DisplayName("deleteById - Successfully deletes a user by ID")
    void testDeleteById_Success() throws Exception {
        // Arrange
        doNothing().when(userService).deleteById(USER_ID);

        // Act & Assert
        mockMvc.perform(delete(BASE_URL + "/" + USER_ID))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(userService, times(1)).deleteById(USER_ID);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}