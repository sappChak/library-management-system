package org.example.bookstore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.example.bookstore.dto.request.user.ChangeUserRolesRequest;
import org.example.bookstore.dto.request.user.UserRequest;
import org.example.bookstore.dto.response.user.UserResponse;
import org.example.bookstore.mapper.UserMapper;
import org.example.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RolesAllowed("ADMIN")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Create a new user", description = "Registers a new user with roles")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest userRequest) {
        var user = userMapper.toEntity(userRequest);
        var savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(savedUser));
    }

    @Operation(summary = "Change user's permissions", description = "Assign or remove roles from a user")
    @PutMapping("/{userId}/roles")
    public ResponseEntity<UserResponse> changeUserRoles(
            @PathVariable Long userId,
            @Valid @RequestBody ChangeUserRolesRequest newRoles) {
        var savedUser = userService.changeUserRoles(userId, newRoles.getRoleIds());
        return ResponseEntity.ok(userMapper.toResponse(savedUser));
    }

    @Operation(summary = "Get current user", description = "Retrieve details of the currently authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userService.getUserByUsername(authentication.getName());
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @Operation(summary = "Get user by ID", description = "Retrieve details of a user by ID")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        var user = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var users = userService.getAllUsers();
        var response = users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get number of users", description = "Retrieve the number of users")
    @GetMapping("/count")
    public ResponseEntity<Long> getUsersCount() {
        return ResponseEntity.ok(userService.getUsersCount());
    }

    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
