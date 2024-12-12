package org.example.library.dto.request.user;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for changing user roles")
public class ChangeUserRolesRequest {

        @NotNull
        @NotEmpty
        @Schema(description = "Set of role IDs assigned to the user", example = "[1, 2]")
        private Set<Long> roleIds;

}
