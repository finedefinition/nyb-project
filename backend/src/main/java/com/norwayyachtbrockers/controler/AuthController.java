package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;
import com.norwayyachtbrockers.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user",
            description = "Saves a new user to AWS Cognito User Pool")
    public String registerUser(@RequestBody UserRegistrationRequestDto userDto) {
        userService.register(userDto);
        return "User registered successfully";
    }

    @PostMapping("/login")
    @Operation(summary = "Login an existing user",
            description = "Creates a new JWT token for user authorization")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return userService.authenticate(request);
    }
}
