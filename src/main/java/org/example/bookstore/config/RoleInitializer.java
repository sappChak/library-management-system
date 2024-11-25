package org.example.bookstore.config;

import org.example.bookstore.entity.Role;
import org.example.bookstore.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

        @Bean
        CommandLineRunner initRoles(RoleRepository roleRepository) {
                return args -> {
                        if (roleRepository.findByRoleName("ROLE_USER").isEmpty()) {
                                roleRepository.save(new Role(null, "ROLE_USER"));
                        }
                        if (roleRepository.findByRoleName("ROLE_ADMIN").isEmpty()) {
                                roleRepository.save(new Role(null, "ROLE_ADMIN"));
                        }
                };
        }
}
