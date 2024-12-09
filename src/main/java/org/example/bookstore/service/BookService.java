package org.example.bookstore.service;

import java.util.List;

import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Transaction;
import org.example.bookstore.entity.enums.ActionType;
import org.example.bookstore.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final TransactionService transactionService;

    public Book addBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        return bookRepository.save(book);
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Page<Book> getAvailableBooks(int page, int size) {
        return bookRepository.findByAvailableCopiesGreaterThan(0, PageRequest.of(page, size));
    }

    public Long getAvailableBooksCount() {
        return bookRepository.countByAvailableCopiesGreaterThan(0);
    }

    public List<Book> getBorrowedBooks(Long userId) {
        return transactionService.getTransactionsWithActiveBorrow(userId).stream()
                .filter(transaction -> transaction.getAction() == ActionType.BORROW)
                .map(Transaction::getBook)
                .toList();
    }

    public Long getBorrowedBooksCount(Long userId) {
        return transactionService.getTransactionsWithActiveBorrow(userId).stream()
                .filter(transaction -> transaction.getAction() == ActionType.BORROW)
                .count();
    }

    public Page<Book> searchBooks(String query, int page, int size) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query,
                PageRequest.of(page, size));
    }

    @Transactional
    public void borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        if (book.getAvailableCopies() == 0) {
            logger.error("No available copies of book with ID: {}.", bookId);
            throw new IllegalArgumentException("No available copies of this book.");
        }

        transactionService.recordTransaction(userId, book, ActionType.BORROW);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }

    @Transactional
    public void returnBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        transactionService.recordTransaction(userId, book, ActionType.RETURN);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new EntityNotFoundException("Book not found with ID: " + bookId);
        }
        bookRepository.deleteById(bookId);
    }
}
