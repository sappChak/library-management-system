package org.example.bookstore.service;

import org.example.bookstore.dto.UserDTO;
import org.example.bookstore.dto.request.SignupRequest;
import org.example.bookstore.entity.User;
import org.example.bookstore.exception.UserExistsException;
import org.example.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_success() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setUsername("testUser");
        signupRequest.setPassword("password123");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // Act
        userService.createUser(signupRequest);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("password123");
    }

    @Test
    void createUser_emailAlreadyExists() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        UserExistsException exception = assertThrows(UserExistsException.class, () -> {
            userService.createUser(signupRequest);
        });
        assertEquals("Email already in use. Please use a different email.", exception.getMessage());
    }

    @Test
    void createUser_usernameAlreadyExists() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testUser");

        when(userRepository.existsByUsername("testUser")).thenReturn(true);

        // Act & Assert
        UserExistsException exception = assertThrows(UserExistsException.class, () -> {
            userService.createUser(signupRequest);
        });
        assertEquals("Username already in use. Please choose a different username.", exception.getMessage());
    }

    @Test
    void updateUser_success() {
        // Arrange
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        User existingUser = new User();
        existingUser.setUsername("testUser");
        existingUser.setEmail("old@example.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("new@example.com");

        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Act
        User updatedUser = userService.updateUser(userDTO, principal);

        // Assert
        assertEquals("new@example.com", updatedUser.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void getUserByPrincipal_success() {
        // Arrange
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getCurrentUser(principal);

        // Assert
        assertEquals("testUser", foundUser.getUsername());
    }

    @Test
    void getUserByPrincipal_userNotFound() {
        // Arrange
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("nonExistentUser");

        when(userRepository.findUserByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.getCurrentUser(principal));
    }
}
