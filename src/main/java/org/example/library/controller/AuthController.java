package org.example.library.controller;

import org.example.library.dto.request.auth.LoginRequest;
import org.example.library.dto.request.auth.SignupRequest;
import org.example.library.dto.response.JwtTokenSuccessResponse;
import org.example.library.dto.response.MessageResponse;
import org.example.library.mapper.UserMapper;
import org.example.library.security.JwtTokenProvider;
import org.example.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Login user", description = "Login user with username and password")
    @PostMapping("/login")
    public ResponseEntity<JwtTokenSuccessResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new JwtTokenSuccessResponse(jwtTokenProvider.generateToken(authentication)));
    }

    @Operation(summary = "Register user", description = "Register user with username, email and password")
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest userRequest) {
        userService.createUser(userMapper.toEntity(userRequest));
        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }
}
