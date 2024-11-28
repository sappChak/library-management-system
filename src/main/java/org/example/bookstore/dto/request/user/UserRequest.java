package org.example.bookstore.dto.request.user;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for creating or updating a user")
public class UserRequest {

        @Schema(description = "Unique username for the user", example = "konotop_401", required = true)
        @NotBlank
        private String username;

        @Schema(description = "User's email address", example = "konotop401@gmail.com", required = true)
        @Email
        @NotBlank
        private String email;

        @Schema(description = "User's password", example = "P@ssw0rd123", required = true)
        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters")
        private String password;

        @Schema(description = "Set of role IDs assigned to the user", example = "[1, 2]")
        private Set<Long> roleIds;
}
