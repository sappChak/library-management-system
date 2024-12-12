package org.example.library.dto.request.transaction;

import org.example.library.entity.enums.ActionType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload for creating a transaction")
public class CreateTransactionRequest {

        @NotNull
        @Schema(description = "ID of the book being transacted", example = "10")
        private Long bookId;

        @NotBlank
        @Schema(description = "Action performed (e.g., borrow, return)", example = "borrow")
        private ActionType action;
}
