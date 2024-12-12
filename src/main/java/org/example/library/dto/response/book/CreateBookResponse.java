package org.example.library.dto.response.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response payload for book details")
public class CreateBookResponse {

    @Schema(description = "ID of the book", example = "1")
    private Long bookId;

    @Schema(description = "Title of the book", example = "The Catcher in the Rye")
    private String title;

    @Schema(description = "Author of the book", example = "J.D. Salinger")
    private String author;

    @Schema(description = "ISBN of the book", example = "978-0-316-76948-0")
    private String isbn;

    @Schema(description = "Number of available copies", example = "5")
    private int availableCopies;
}
