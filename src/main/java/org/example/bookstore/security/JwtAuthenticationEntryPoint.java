package org.example.bookstore.security;

import java.io.IOException;
import java.time.Instant;

import org.example.bookstore.dto.response.InvalidLoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

        private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
        private final ObjectMapper objectMapper;

        public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
                this.objectMapper = objectMapper;
        }

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException authException) throws IOException {

                logger.warn("Unauthorized access attempt to {} - Reason: {}", request.getRequestURI(),
                                authException.getMessage());

                InvalidLoginResponse loginResponse = new InvalidLoginResponse(
                                Instant.now().toString(),
                                "Unauthorized access",
                                request.getRequestURI());

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
        }
}
