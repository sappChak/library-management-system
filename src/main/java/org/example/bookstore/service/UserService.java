package org.example.bookstore.service;

import java.util.List;

import org.example.bookstore.entity.User;
import org.example.bookstore.exception.UserExistsException;
import org.example.bookstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user) {
        validateUniqueEmail(user.getEmail());
        validateUniqueUsername(user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles().isEmpty()) {
            user.getRoles().add(roleService.getRoleByName("ROLE_USER"));
        }

        try {
            logger.info("Saving new user: {}", user.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error saving user: {}", e.getMessage());
            throw new UserExistsException("User already exists. Please use a different email or username.");
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        logger.info("Fetching user by ID: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        logger.info("Fetching user by username: {}", username);

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            logger.warn("Attempted to delete non-existent user with ID: {}", id);
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            logger.warn("Attempted registration with existing email: {}", email);
            throw new UserExistsException("Email already in use. Please use a different email.");
        }
    }

    private void validateUniqueUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            logger.warn("Attempted registration with existing username: {}", username);
            throw new UserExistsException("Username already in use. Please choose a different username.");
        }
    }

    public User updateUser(Long id, User user) {
        logger.info("Updating user with ID: {}", id);

        var existingUser = getUserById(id);

        validateUpdateEmail(existingUser.getEmail(), user.getEmail());
        validateUpdateUsername(existingUser.getUsername(), user.getUsername());

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        existingUser.setRoles(user.getRoles());
        return userRepository.save(existingUser);
    }

    private void validateUpdateEmail(String existingEmail, String newEmail) {
        if (!existingEmail.equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            logger.warn("Attempted to update email to an existing email: {}", newEmail);
            throw new UserExistsException("Email already in use. Please use a different email.");
        }
    }

    private void validateUpdateUsername(String existingUsername, String newUsername) {
        if (!existingUsername.equals(newUsername) && userRepository.existsByUsername(newUsername)) {
            logger.warn("Attempted to update username to an existing username: {}", newUsername);
            throw new UserExistsException("Username already in use. Please choose a different username.");
        }
    }
}
