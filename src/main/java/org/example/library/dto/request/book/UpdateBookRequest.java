package org.example.library.dto.request.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload for updating a book")
public class UpdateBookRequest {

        @NotBlank
        @Schema(description = "Title of the book", example = "To Kill a Mockingbird")
        private String title;

        @NotBlank
        @Schema(description = "Author of the book", example = "Harper Lee")
        private String author;

        @NotBlank
        @Schema(description = "ISBN of the book", example = "978-0-06-112008-4")
        private String isbn;

        @PositiveOrZero
        @Schema(description = "Number of available copies", example = "10")
        private int availableCopies;
}
