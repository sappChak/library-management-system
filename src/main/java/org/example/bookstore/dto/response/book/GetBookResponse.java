package org.example.bookstore.dto.response.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response payload for retrieving a book's details")
public class GetBookResponse {

    @Schema(description = "ID of the book", example = "1")
    private Long bookId;

    @Schema(description = "Title of the book", example = "1984")
    private String title;

    @Schema(description = "Author of the book", example = "George Orwell")
    private String author;

    @Schema(description = "ISBN of the book", example = "978-0-452-28423-4")
    private String isbn;

    @Schema(description = "Number of available copies", example = "3")
    private int availableCopies;
}
