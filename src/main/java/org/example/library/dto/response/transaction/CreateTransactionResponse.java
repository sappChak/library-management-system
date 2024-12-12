package org.example.library.dto.response.transaction;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response payload for a newly created transaction")
public class CreateTransactionResponse {

    @Schema(description = "ID of the transaction", example = "1001")
    private Long transactionId;

    @Schema(description = "ID of the user who performed the transaction", example = "1")
    private Long userId;

    @Schema(description = "ID of the book involved in the transaction", example = "10")
    private Long bookId;

    @Schema(description = "Action performed (e.g., borrow, return)", example = "borrow")
    private String action;

    @Schema(description = "Timestamp of the transaction", example = "2024-11-25T14:30:00")
    private LocalDateTime date;
}
