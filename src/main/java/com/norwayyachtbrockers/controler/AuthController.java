package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.constants.ApplicationConstants;
import com.norwayyachtbrockers.dto.request.UserLoginRequestDto;
import com.norwayyachtbrockers.dto.request.UserRegistrationRequestDto;
import com.norwayyachtbrockers.dto.response.ResponseDto;
import com.norwayyachtbrockers.dto.response.UserLoginResponseDto;
import com.norwayyachtbrockers.service.AuthService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody UserRegistrationRequestDto userDto) {
        authService.register(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ApplicationConstants.STATUS_201, ApplicationConstants.MESSAGE_201));
    }

    @PostMapping("/confirm")
    public ResponseEntity<UserLoginResponseDto> confirmUser(@RequestParam String email,
                                                            @RequestParam String confirmationCode,
                                                            @RequestParam String password) {
        UserLoginResponseDto userLoginResponseDto = authService.confirmUser(email, confirmationCode, password);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userLoginResponseDto);
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

    @PostMapping("/forgotPassword")
    public ResponseEntity<ResponseDto> forgotPassword(@RequestParam String email) {
        authService.initiatePasswordRecovery(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(ApplicationConstants.STATUS_200, ApplicationConstants.MESSAGE_200));
    }

    @PostMapping("/confirmForgotPassword")
    public ResponseEntity<ResponseDto> confirmForgotPassword(@RequestParam String email,
                                                        @RequestParam String confirmationCode,
                                                        @RequestParam String newPassword) {
        authService.confirmPasswordRecovery(email, confirmationCode, newPassword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(ApplicationConstants.STATUS_200, ApplicationConstants.MESSAGE_200));
    }

}
