package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.UpdateUserRequestDto;
import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;
import com.norwayyachtbrockers.service.AuthService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Data
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequestDto userDto) {
        authService.register(userDto);
        return ResponseEntity.status(201).body("User registered successfully");
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authService.authenticate(request);
    }

//    @PutMapping("/updateUser")
//    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequestDto request) {
//        authService.updateUserAttributesAndManageGroups(
//                request.getUsername(),
//                request.getFirstName(),
//                request.getLastName(),
//                request.getRole(),
//                userPoolId);
//
//        return ResponseEntity.ok("User updated successfully");
//    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        authService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
