package org.example.bookstore.service;

import java.security.Principal;

import org.example.bookstore.dto.UserDTO;
import org.example.bookstore.dto.request.SignupRequest;
import org.example.bookstore.entity.Role;
import org.example.bookstore.entity.User;
import org.example.bookstore.exception.UserExistsException;
import org.example.bookstore.repository.RoleRepository;
import org.example.bookstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(SignupRequest userIn) {
        if (userRepository.existsByEmail(userIn.getEmail())) {
            logger.warn("Attempted registration with existing email: {}", userIn.getEmail());
            throw new UserExistsException("Email already in use. Please use a different email.");
        }

        if (userRepository.existsByUsername(userIn.getUsername())) {
            logger.warn("Attempted registration with existing username: {}", userIn.getUsername());
            throw new UserExistsException("Username already in use. Please choose a different username.");
        }

        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));

        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found in the database"));
        user.getRoles().add(userRole);

        try {
            logger.info("Saving new user: {}", userIn.getEmail());
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error saving user: {}", e.getMessage());
            throw new RuntimeException("Error creating user. Please try again later.");
        }
    }

    @Transactional
    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }

        logger.info("Updating user: {}", user.getUsername());
        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        if (principal == null || principal.getName() == null) {
            logger.error("Principal is null or invalid");
            throw new UsernameNotFoundException("Principal is invalid");
        }

        String username = principal.getName();
        logger.info("Fetching user by username: {}", username);

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User getUserById(Long id) {
        logger.info("Fetching user by ID: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
    }

    public User getUserByUsername(String username) {
        logger.info("Fetching user by username: {}", username);

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
