package org.example.bookstore.service;

import org.example.bookstore.entity.User;
import org.example.bookstore.exception.UserExistsException;
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
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Attempted registration with existing email: {}", user.getEmail());
            throw new UserExistsException("Email already in use. Please use a different email.");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("Attempted registration with existing username: {}", user.getUsername());
            throw new UserExistsException("Username already in use. Please choose a different username.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.getRoleByName("ROLE_USER"));

        try {
            logger.info("Saving new user: {}", user.getEmail());
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error saving user: {}", e.getMessage());
            throw new RuntimeException("Error creating user. Please try again later.");
        }
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
