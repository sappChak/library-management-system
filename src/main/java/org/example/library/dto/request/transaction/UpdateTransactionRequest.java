package org.example.library.dto.request.transaction;

import java.time.LocalDateTime;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload for updating a transaction")
public class UpdateTransactionRequest {

        @NotBlank
        @Schema(description = "Action performed (e.g., borrow, return)", example = "return")
        private String action;

        @NotNull
        @Schema(description = "Timestamp of the transaction update", example = "2024-11-25T14:30:00")
        private LocalDateTime date;
}
