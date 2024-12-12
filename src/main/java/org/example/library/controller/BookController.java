package org.example.library.controller;

import java.util.List;

import org.example.library.dto.request.book.CreateBookRequest;
import org.example.library.dto.response.book.GetBookResponse;
import org.example.library.entity.Book;
import org.example.library.mapper.BookMapper;
import org.example.library.security.CustomUserDetails;
import org.example.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library.")
    @RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<List<GetBookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookMapper.toResponseDtoList(bookService.getAllBooks()));
    }

    @Operation(summary = "Get paginated available books", description = "Retrieve a paginated list of books available in the library.")
    @GetMapping("/available")
    public ResponseEntity<Page<GetBookResponse>> getAvailableBooksPaginated(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookMapper.toResponseDtoPage(bookService.getAvailableBooks(page, size)));
    }

    @Operation(summary = "Get number of available books", description = "Retrieve the number of books available in the library.")
    @GetMapping("/available/count")
    public ResponseEntity<Long> getAvailableBooksCount() {
        return ResponseEntity.ok(bookService.getAvailableBooksCount());
    }

    @Operation(summary = "Search books by title, author or isbn", description = "Search books by title, author or ISBN.")
    @GetMapping("/search")
    public ResponseEntity<Page<GetBookResponse>> searchBooks(@RequestParam String query,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookMapper.toResponseDtoPage(bookService.searchBooks(query, page, size)));
    }

    @Operation(summary = "Get borrowed books", description = "Retrieve a list of books borrowed by me.")
    @GetMapping("/borrowed")
    public ResponseEntity<List<GetBookResponse>> getBorrowedBooks(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(bookMapper.toResponseDtoList(bookService.getBorrowedBooks(userDetails.getId())));
    }

    @Operation(summary = "Get borrowed books count", description = "Retrieve the number of books borrowed by me.")
    @GetMapping("/borrowed/count")
    public ResponseEntity<Long> getBorrowedBooksCount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(bookService.getBorrowedBooksCount(userDetails.getId()));
    }

    @Operation(summary = "Add a new book", description = "Provide the details of the book to add it to the library.")
    @PostMapping
    public ResponseEntity<GetBookResponse> addBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        Book book = bookService.addBook(bookMapper.toEntity(createBookRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.toResponseDto(book));
    }

    @Operation(summary = "Borrow a book", description = "Borrow a book from the library.")
    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Void> borrowBook(@AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long bookId) {
        bookService.borrowBook(bookId, userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Return a book", description = "Return a borrowed book to the library.")
    @PostMapping("/return/{bookId}")
    public ResponseEntity<Void> returnBook(@AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long bookId) {
        bookService.returnBook(bookId, userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a book", description = "Delete a book by its ID.")
    @RolesAllowed("ADMIN")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
