package org.example.bookstore.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.bookstore.dto.request.auth.LoginRequest;
import org.example.bookstore.dto.request.auth.SignupRequest;
import org.example.bookstore.entity.User;
import org.example.bookstore.mapper.UserMapper;
import org.example.bookstore.security.CustomUserDetailService;
import org.example.bookstore.security.JwtTokenProvider;
import org.example.bookstore.service.UserService;
import org.example.bookstore.validations.ResponseErrorValidation;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

                private static final String MOCK_USERNAME = "testuser@mail.com";
                private static final String MOCK_PASSWORD = "password123";
                private static final String MOCK_JWT_TOKEN = "mock_jwt_token";

                @Autowired
                private MockMvc mockMvc;

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
                private UserMapper userMapper;

                @Test
                @WithMockUser
                void testLoginReturnsJwtTokenOnSuccessfulAuthentication() throws Exception {
                                // Arrange
                                Authentication authentication = mock(Authentication.class);
                                when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                                                                .thenReturn(authentication);
                                when(jwtTokenProvider.generateToken(authentication)).thenReturn(MOCK_JWT_TOKEN);

                                String requestJson = new ObjectMapper()
                                                                .writeValueAsString(new LoginRequest(MOCK_USERNAME,
                                                                                                MOCK_PASSWORD));

                                // Act and Assert
                                mockMvc.perform(post("/api/auth/login")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson)
                                                                .with(csrf()))
                                                                .andExpect(status().isOk())
                                                                .andExpect(jsonPath("$.accessToken").value(MOCK_JWT_TOKEN));

                                // Verify the interactions
                                ArgumentCaptor<UsernamePasswordAuthenticationToken> captor = ArgumentCaptor
                                                                .forClass(UsernamePasswordAuthenticationToken.class);

                                verify(authenticationManager).authenticate(captor.capture());
                                UsernamePasswordAuthenticationToken capturedAuth = captor.getValue();
                                assertEquals(MOCK_USERNAME, capturedAuth.getPrincipal());
                                assertEquals(MOCK_PASSWORD, capturedAuth.getCredentials());

                                verify(jwtTokenProvider).generateToken(authentication);
                }

                @Test
                @WithMockUser
                void testLoginFailsWhenCredentialsAreInvalid() throws Exception {
                                // Mocking an authentication failure
                                when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                                                                .thenThrow(new BadCredentialsException(
                                                                                                "Invalid credentials"));

                                String requestJson = new ObjectMapper()
                                                                .writeValueAsString(new LoginRequest(MOCK_USERNAME,
                                                                                                MOCK_PASSWORD));

                                // Perform the login request and validate the response
                                mockMvc.perform(post("/api/auth/login")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson)
                                                                .with(csrf()))
                                                                .andExpect(status().isUnauthorized())
                                                                .andExpect(jsonPath("$.message").value(
                                                                                                "Invalid credentials"));

                                // Verify the interactions
                                verify(authenticationManager).authenticate(
                                                                any(UsernamePasswordAuthenticationToken.class));
                                verify(jwtTokenProvider, never()).generateToken(any());
                }

                @Test
                @WithMockUser
                void testLoginFailsWithoutCsrfToken() throws Exception {
                                String requestJson = new ObjectMapper()
                                                                .writeValueAsString(new LoginRequest(MOCK_USERNAME,
                                                                                                MOCK_PASSWORD));
                                // Perform the login request without CSRF token and validate the response
                                mockMvc.perform(post("/api/auth/login")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson))
                                                                .andExpect(status().isForbidden());
                }

                @Test
                void testAuthenticateUser_InvalidCredentials() throws Exception {
                                LoginRequest loginRequest = new LoginRequest();
                                loginRequest.setUsername("invaliduser@@mail.com");
                                loginRequest.setPassword("wrong");
                                String requestJson = new ObjectMapper().writeValueAsString(loginRequest);

                                // Mock the authenticationManager to throw
                                // BadCredentialsException when
                                // authentication is attempted
                                when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                                                                .thenThrow(new BadCredentialsException(
                                                                                                "Invalid credentials"));

                                // Perform the login request and expect a 401
                                // Unauthorized status
                                mockMvc.perform(post("/api/auth/login")
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(requestJson)
                                                                .with(csrf()))
                                                                .andExpect(status().isUnauthorized());
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

}
