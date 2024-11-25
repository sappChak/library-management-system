package org.example.bookstore.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Request object for user login")
public class LoginRequest {

        @NotEmpty(message = "Username cannot be empty")
        @Schema(description = "Username of the user", example = "john_doe")
        private String username;

        @NotEmpty(message = "Password cannot be empty")
        @Schema(description = "Password of the user", example = "securePassword123")
        private String password;
}
