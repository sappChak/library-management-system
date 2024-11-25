package org.example.bookstore.service;

import java.util.List;

import org.example.bookstore.dto.request.book.CreateBookRequest;
import org.example.bookstore.dto.request.book.UpdateBookRequest;
import org.example.bookstore.dto.response.book.GetBookResponse;
import org.example.bookstore.entity.Book;
import org.example.bookstore.mapper.BookMapper;
import org.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public GetBookResponse addBook(CreateBookRequest createBookRequestDTO) {
        if (bookRepository.existsByIsbn(createBookRequestDTO.getIsbn())) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        Book savedBook = bookRepository.save(bookMapper.toEntity(createBookRequestDTO));
        return bookMapper.toResponseDto(savedBook);
    }

    public GetBookResponse updateBook(Long bookId, UpdateBookRequest updateBookRequestDTO) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        book.setTitle(updateBookRequestDTO.getTitle());
        book.setAuthor(updateBookRequestDTO.getAuthor());
        book.setIsbn(updateBookRequestDTO.getIsbn());
        book.setAvailableCopies(updateBookRequestDTO.getAvailableCopies());

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponseDto(updatedBook);
    }

    public GetBookResponse getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
        return bookMapper.toResponseDto(book);
    }

    public List<GetBookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toResponseDtoList(books);
    }

    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new EntityNotFoundException("Book not found with ID: " + bookId);
        }
        bookRepository.deleteById(bookId);
    }
}
