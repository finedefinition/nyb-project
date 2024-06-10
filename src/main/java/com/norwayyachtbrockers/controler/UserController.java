package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.constants.ApplicationConstants;
import com.norwayyachtbrockers.dto.response.ResponseDto;
import com.norwayyachtbrockers.dto.response.UserFavouriteYachtsResponseDto;
import com.norwayyachtbrockers.dto.response.UserResponseDto;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.enums.UserRoles;
import com.norwayyachtbrockers.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        UserRoles userRole = user.getUserRoles();
        user.setUserRoles(userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                           @PathVariable Long id) {
        return ResponseEntity.ok(userService.updateUser(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cognitoSub}/favouriteYachts/{yachtId}")
    @PreAuthorize("hasRole('USER') and #cognitoSub == authentication.principal.claims['sub']")
    public ResponseEntity<?> addFavouriteYacht(@PathVariable String cognitoSub, @PathVariable Long yachtId) {
        userService.addFavouriteYachtToUser(cognitoSub, yachtId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ApplicationConstants.STATUS_201,
                        ApplicationConstants.MESSAGE_201_FAVOURITE_YACHTS));
    }

    @GetMapping(value = "/{cognitoSub}/favouriteYachts", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') and #cognitoSub == authentication.principal.claims['sub']")
    public ResponseEntity<UserFavouriteYachtsResponseDto> getFavouriteYachts(@PathVariable String cognitoSub) {
        UserFavouriteYachtsResponseDto dto = userService.getFavouriteYachts(cognitoSub);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{cognitoSub}/favouriteYachts/{yachtId}")
    @PreAuthorize("hasRole('USER') and #cognitoSub == authentication.principal.claims['sub']")
    public ResponseEntity<?> deleteFavouriteYachts(@PathVariable String cognitoSub, @PathVariable Long yachtId) {
        try {
            userService.removeFavouriteYacht(cognitoSub, yachtId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ApplicationConstants.STATUS_204,
                            ApplicationConstants.MESSAGE_204));
        } catch (AppEntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
