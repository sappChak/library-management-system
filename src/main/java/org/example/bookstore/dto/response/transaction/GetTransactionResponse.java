package org.example.bookstore.dto.response.transaction;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response payload for retrieving a transaction's details")
public class GetTransactionResponse {

    @Schema(description = "ID of the transaction", example = "1001")
    private Long id;

    @Schema(description = "ID of the user who performed the transaction", example = "1")
    private Long userId;

    @Schema(description = "Name of the user who performed the transaction", example = "Andrii Konotop")
    private String userName;

    @Schema(description = "ID of the book involved in the transaction", example = "10")
    private Long bookId;

    @Schema(description = "Title of the book involved in the transaction", example = "1984")
    private String bookTitle;

    @Schema(description = "Action performed (e.g., borrow, return)", example = "borrow")
    private String action;

    @Schema(description = "Timestamp of the transaction", example = "2024-11-25T14:30:00")
    private LocalDateTime date;
}
