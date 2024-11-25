package org.example.bookstore.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.example.bookstore.dto.request.LoginRequest;
import org.example.bookstore.dto.request.SignupRequest;
import org.example.bookstore.dto.response.JwtTokenSuccessResponse;
import org.example.bookstore.dto.response.MessageResponse;
import org.example.bookstore.security.JwtTokenProvider;
import org.example.bookstore.service.UserService;
import org.example.bookstore.validations.ResponseErrorValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ResponseErrorValidation responseErrorValidation;
    private final UserService userService;

    @Operation(summary = "Login user", description = "Login user with username and password")
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult) {

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors))
            return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new JwtTokenSuccessResponse(true, jwtTokenProvider.generateToken(authentication)));
    }

    @Operation(summary = "Register user", description = "Register user with username, email and password")
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest,
            BindingResult bindingResult) {

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors))
            return errors;

        userService.createUser(signupRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }
}
