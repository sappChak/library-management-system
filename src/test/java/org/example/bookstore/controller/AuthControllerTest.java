package org.example.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.bookstore.dto.request.auth.LoginRequest;
import org.example.bookstore.dto.request.auth.SignupRequest;
import org.example.bookstore.entity.User;
import org.example.bookstore.mapper.UserMapper;
import org.example.bookstore.security.JwtTokenProvider;
import org.example.bookstore.security.CustomUserDetailService;
import org.example.bookstore.service.UserService;
import org.example.bookstore.validations.ResponseErrorValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

                private final MockMvc mockMvc;

                @MockBean
                private JwtTokenProvider jwtTokenProvider;

                @MockBean
                private AuthenticationManager authenticationManager;

                @MockBean
                private ResponseErrorValidation responseErrorValidation;

                @MockBean
                private CustomUserDetailService customUserDetailService;

                @MockBean
                private UserService userService;

                @MockBean
                private UserMapper userMapper; // Add UserMapper as a mock

                @Autowired
                public AuthControllerTest(MockMvc mockMvc) {
                                this.mockMvc = mockMvc;
                }

                @Test
                @WithMockUser
                void testAuthenticateUser_Success() throws Exception {
                                // Mocking JWT token
                                String mockJwtToken = "mock_jwt_token";

                                LoginRequest loginRequest = new LoginRequest();
                                loginRequest.setUsername("testuser@mail.com");
                                loginRequest.setPassword("password123");
                                String requestJson = new ObjectMapper().writeValueAsString(loginRequest);

                                // Mock authentication request
                                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                                                                loginRequest.getUsername(),
                                                                loginRequest.getPassword());
                                Authentication authentication = mock(Authentication.class);
                                when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                                                                .thenReturn(authentication);
                                when(jwtTokenProvider.generateToken(authentication)).thenReturn(mockJwtToken);

                                // Perform the login request and validate the response
                                mockMvc.perform(post("/api/auth/login")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson)
                                                                .with(csrf()))
                                                                .andExpect(status().isOk())
                                                                .andExpect(jsonPath("$.success").value(true))
                                                                .andExpect(jsonPath("$.token").value(mockJwtToken));
                }

                @Test
                void testAuthenticateUser_InvalidCredentials() throws Exception {
                                LoginRequest loginRequest = new LoginRequest();
                                loginRequest.setUsername("invaliduser@@mail.com");
                                loginRequest.setPassword("wrong");
                                String requestJson = new ObjectMapper().writeValueAsString(loginRequest);

                                // Mock the authenticationManager to throw BadCredentialsException when
                                // authentication is attempted
                                when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                                                                .thenThrow(new BadCredentialsException(
                                                                                                "Invalid credentials"));

                                // Perform the login request and expect a 401 Unauthorized status
                                mockMvc.perform(post("/api/auth/login")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson)
                                                                .with(csrf()))
                                                                .andExpect(status().isUnauthorized());
                }

                @Test
                @WithMockUser
                void testAuthenticateUser_ValidationErrors() throws Exception {
                                LoginRequest loginRequest = new LoginRequest();
                                loginRequest.setUsername("");
                                loginRequest.setPassword("");
                                String requestJson = new ObjectMapper().writeValueAsString(loginRequest);

                                BindingResult bindingResult = mock(BindingResult.class);
                                when(bindingResult.hasErrors()).thenReturn(true);
                                when(bindingResult.getAllErrors())
                                                                .thenReturn(List.of(new ObjectError("loginRequest",
                                                                                                "Validation error")));
                                when(responseErrorValidation.mapValidationService(bindingResult))
                                                                .thenReturn(ResponseEntity.badRequest().body(
                                                                                                "Validation error"));

                                // Ensure the mock is used by the controller
                                doReturn(ResponseEntity.badRequest().body("Validation error"))
                                                                .when(responseErrorValidation)
                                                                .mapValidationService(any(BindingResult.class));

                                mockMvc.perform(post("/api/auth/login")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson)
                                                                .with(csrf()))
                                                                .andExpect(status().isBadRequest())
                                                                .andExpect(jsonPath("$").value("Validation error"));
                }

                @Test
                @WithMockUser
                void testRegisterUser_Success() throws Exception {
                                SignupRequest signupRequest = new SignupRequest();
                                signupRequest.setEmail("newuser@mail.com");
                                signupRequest.setUsername("username");
                                signupRequest.setPassword("password123");
                                signupRequest.setConfirmPassword("password123");
                                String requestJson = new ObjectMapper().writeValueAsString(signupRequest);

                                // Mock the userMapper behavior
                                User userEntity = new User();
                                when(userMapper.toEntity(signupRequest)).thenReturn(userEntity);

                                mockMvc.perform(post("/api/auth/register")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson)
                                                                .with(csrf()))
                                                                .andExpect(status().isOk())
                                                                .andExpect(jsonPath("$.message").value(
                                                                                                "User registered successfully."));
                }

                @Test
                @WithMockUser
                void testRegisterUser_ValidationErrors() throws Exception {
                                SignupRequest signupRequest = new SignupRequest();
                                signupRequest.setEmail("user@mail.com");
                                signupRequest.setUsername("username");
                                signupRequest.setPassword("password123");
                                signupRequest.setConfirmPassword("password122");
                                String requestJson = new ObjectMapper().writeValueAsString(signupRequest);

                                BindingResult bindingResult = mock(BindingResult.class);
                                when(bindingResult.hasErrors()).thenReturn(true);
                                when(bindingResult.getAllErrors())
                                                                .thenReturn(List.of(new ObjectError("signupRequest",
                                                                                                "Validation error")));
                                when(responseErrorValidation.mapValidationService(bindingResult))
                                                                .thenReturn(ResponseEntity.badRequest().body(
                                                                                                "Validation error"));

                                doReturn(ResponseEntity.badRequest().body("Validation error"))
                                                                .when(responseErrorValidation)
                                                                .mapValidationService(any(BindingResult.class));

                                mockMvc.perform(post("/api/auth/register")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson)
                                                                .with(csrf()))
                                                                .andExpect(status().isBadRequest())
                                                                .andExpect(jsonPath("$").value("Validation error"));
                }
}
