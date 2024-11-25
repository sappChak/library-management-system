package org.example.bookstore.controller;

import java.util.List;

import org.example.bookstore.dto.request.book.CreateBookRequest;
import org.example.bookstore.dto.request.book.UpdateBookRequest;
import org.example.bookstore.dto.response.book.GetBookResponse;
import org.example.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Add a new book", description = "Provide the details of the book to add it to the library.")
    public ResponseEntity<GetBookResponse> addBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(createBookRequest));
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "Update a book", description = "Update the details of an existing book by its ID.")
    public ResponseEntity<GetBookResponse> updateBook(
            @PathVariable Long bookId,
            @RequestBody @Valid UpdateBookRequest updateBookRequest) {
        return ResponseEntity.ok(bookService.updateBook(bookId, updateBookRequest));
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Get a book by ID", description = "Retrieve the details of a specific book by its ID.")
    public ResponseEntity<GetBookResponse> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library.")
    public ResponseEntity<List<GetBookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @DeleteMapping("/{bookId}")
    @Operation(summary = "Delete a book", description = "Delete a book by its ID.")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
