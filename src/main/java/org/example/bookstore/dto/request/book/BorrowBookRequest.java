package org.example.bookstore.dto.request.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BorrowBookRequest {

        @NotBlank
        @Schema(description = "ID of the book to borrow", example = "1")
        private Long bookId;

}
