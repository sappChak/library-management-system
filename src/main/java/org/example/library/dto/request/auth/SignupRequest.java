package org.example.library.dto.request.auth;

import org.example.library.annotations.PasswordMatches;
import org.example.library.annotations.ValidEmail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for user registration")
public class SignupRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    @Schema(description = "User's email address", example = "konotop401@gmail.com")
    private String email;

    @NotEmpty(message = "Please enter your username")
    @Schema(description = "Username for the new user", example = "konotop_401")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "Password for the new account", example = "securepassword123")
    private String password;

    @Schema(description = "Password confirmation for the new account", example = "securepassword123")
    private String confirmPassword;
}
