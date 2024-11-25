package org.example.bookstore.controller;

import java.util.List;

import org.example.bookstore.dto.request.book.CreateBookRequest;
import org.example.bookstore.dto.response.book.GetBookResponse;
import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.User;
import org.example.bookstore.mapper.BookMapper;
import org.example.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final BookMapper bookMapper;

    @PostMapping
    @Operation(summary = "Add a new book", description = "Provide the details of the book to add it to the library.")
    public ResponseEntity<GetBookResponse> addBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        Book book = bookService.addBook(bookMapper.toEntity(createBookRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.toResponseDto(book));
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library.")
    public ResponseEntity<List<GetBookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookMapper.toResponseDtoList(bookService.getAllBooks()));
    }

    @PostMapping("/borrow/{bookId}")
    @Operation(summary = "Borrow a book", description = "Borrow a book from the library.")
    public ResponseEntity<Void> borrowBook(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            userId = userDetails.getId();
        }

        bookService.borrowBook(bookId, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookId}")
    @Operation(summary = "Delete a book", description = "Delete a book by its ID.")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
