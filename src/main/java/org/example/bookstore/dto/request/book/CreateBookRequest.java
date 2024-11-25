package org.example.bookstore.dto.request.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload for creating or updating a book")
public class CreateBookRequest {

        @NotBlank
        @Schema(description = "Title of the book", example = "The Catcher in the Rye")
        private String title;

        @NotBlank
        @Schema(description = "Author of the book", example = "J.D. Salinger")
        private String author;

        @NotBlank
        @Schema(description = "ISBN of the book", example = "978-0-316-76948-0")
        private String isbn;

        @PositiveOrZero
        @Schema(description = "Number of available copies", example = "5")
        private int availableCopies;
}
