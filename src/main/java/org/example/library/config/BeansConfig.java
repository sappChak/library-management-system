package org.example.library.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {

        private static final Logger logger = LoggerFactory.getLogger(BeansConfig.class);

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                logger.info("Configuring AuthenticationManager");
                return authConfig.getAuthenticationManager();
        }

        @Bean
        BCryptPasswordEncoder passwordEncoder() {
                logger.info("Configuring BCryptPasswordEncoder");
                return new BCryptPasswordEncoder();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                logger.info("Configuring CORS settings");
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.addAllowedOriginPattern("*");
                config.addAllowedMethod("*");
                config.addAllowedHeader("*");

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);

                logger.info("CORS configured to allow all origins, headers, and methods.");
                return source;
        }
}
