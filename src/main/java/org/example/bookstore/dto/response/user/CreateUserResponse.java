package org.example.bookstore.dto.response.user;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for returning user details")
public class CreateUserResponse {

    @Schema(description = "Unique ID of the user", example = "1")
    private Long id;

    @Schema(description = "Unique username for the user", example = "konotop_401")
    private String username;

    @Schema(description = "User's email address", example = "konotop401@gmail.com")
    private String email;

    @Schema(description = "Set of roles assigned to the user", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
    private Set<String> roles;
}
