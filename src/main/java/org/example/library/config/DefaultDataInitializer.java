
package org.example.library.config;

import java.util.Arrays;

import org.example.library.entity.Role;
import org.example.library.entity.User;
import org.example.library.entity.enums.ERole;
import org.example.library.repository.RoleRepository;
import org.example.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DefaultDataInitializer {

        private static final Logger logger = LoggerFactory.getLogger(DefaultDataInitializer.class);

        @Value("${app.admin.username}")
        private String adminUsername;

        @Value("${app.admin.password}")
        private String adminPassword;

        @Value("${app.admin.email}")
        private String adminEmail;

        @Bean
        CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
                return args -> {
                        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
                                logger.info("Creating default roles.");
                                roleRepository.save(new Role(ERole.ROLE_USER));
                        }
                        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
                                logger.info("Creating default roles.");
                                roleRepository.save(new Role(ERole.ROLE_ADMIN));
                        }

                        if (userRepository.findUserByUsername(adminUsername).isEmpty()) {
                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                                .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found."));

                                logger.info("Creating default admin user.");
                                User adminUser = new User();
                                adminUser.setUsername(adminUsername);
                                adminUser.setEmail(adminEmail);
                                adminUser.setPassword(passwordEncoder.encode(adminPassword));
                                adminUser.setRoles(Arrays.asList(adminRole));
                                userRepository.save(adminUser);
                        }
                };
        }
}
