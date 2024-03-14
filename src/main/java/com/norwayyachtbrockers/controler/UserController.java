package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.response.UserFavouriteYachtsResponseDto;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.enums.UserRoles;
import com.norwayyachtbrockers.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findId(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();

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
        String userEmail = userService.findId(id).getEmail();
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Successfully deleted the User with ID:"
                        + id + " --> \"" + userEmail + "\"");
    }

    @PostMapping("/{userId}/favouriteYachts/{yachtId}")
    public ResponseEntity<?> addFavouriteYacht(@PathVariable Long userId, @PathVariable Long yachtId) {
        userService.addFavouriteYacht(userId, yachtId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/favouriteYachts")
    public ResponseEntity<UserFavouriteYachtsResponseDto> getFavouriteYachts(@PathVariable Long userId) {
        UserFavouriteYachtsResponseDto dto = userService.getFavouriteYachts(userId);
        return ResponseEntity.ok(dto);
    }
}
