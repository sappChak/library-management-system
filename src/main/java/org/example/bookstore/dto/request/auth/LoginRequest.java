package org.example.bookstore.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for user login")
public class LoginRequest {

        @NotEmpty(message = "Username cannot be empty")
        @Schema(description = "Username of the user", example = "konotop_401")
        private String username;

        @NotEmpty(message = "Password cannot be empty")
        @Schema(description = "Password of the user", example = "securepassword123")
        private String password;
}
