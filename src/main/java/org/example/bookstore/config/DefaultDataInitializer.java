
package org.example.bookstore.config;

import org.example.bookstore.entity.Role;
import org.example.bookstore.entity.User;
import org.example.bookstore.repository.RoleRepository;
import org.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultDataInitializer {

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
                        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                                roleRepository.save(new Role(null, "ROLE_USER"));
                        }
                        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                                roleRepository.save(new Role(null, "ROLE_ADMIN"));
                        }

                        if (userRepository.findUserByUsername(adminUsername).isEmpty()) {
                                Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                                                .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found."));

                                User adminUser = new User();
                                adminUser.setUsername(adminUsername);
                                adminUser.setEmail(adminEmail);
                                adminUser.setPassword(passwordEncoder.encode(adminPassword));
                                adminUser.getRoles().add(adminRole);
                                userRepository.save(adminUser);
                        }
                };
        }
}
