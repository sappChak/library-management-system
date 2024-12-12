package org.example.library.service;

import java.time.LocalDateTime;
import java.util.List;

import org.example.library.entity.Book;
import org.example.library.entity.Transaction;
import org.example.library.entity.User;
import org.example.library.entity.enums.ActionType;
import org.example.library.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public void recordTransaction(Long userId, Book book, ActionType action) {
        User user = userService.getUserById(userId);

        validateTransaction(userId, book, action);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setAction(action);
        transaction.setDate(LocalDateTime.now());

        if (action == ActionType.RETURN) {
            deactivateBorrowTransaction(userId, book.getId());
        } else if (action == ActionType.BORROW) {
            transaction.setActive(true);
        }

        logger.info("Recording transaction for user {} for book {} with action {}",
                user.getUsername(), book.getTitle(), action);
        transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + transactionId));
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public List<Transaction> getTransactionsWithActiveBorrow(Long userId) {
        return transactionRepository.findAllByUserIdAndIsActive(userId, true);
    }

    public Long getTotalReturnedBooksCount() {
        return transactionRepository.countAllByActionAndIsActive(ActionType.RETURN, false);
    }

    public Long getActiveBorrowingsCount() {
        return transactionRepository.countAllByActionAndIsActive(ActionType.BORROW, true);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new EntityNotFoundException("Transaction not found with ID: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }

    // Helper methods
    private void validateTransaction(Long userId, Book book, ActionType action) {
        boolean hasActiveBorrow = transactionRepository.existsByUserIdAndBookIdAndActionAndIsActive(
                userId, book.getId(), ActionType.BORROW, true);

        if (action == ActionType.BORROW && hasActiveBorrow) {
            logger.error("User {} already has an active borrow transaction for book {}", userId, book.getTitle());
            throw new IllegalArgumentException("You already have an active borrow transaction for this book.");
        }

        if (action == ActionType.RETURN && !hasActiveBorrow) {
            logger.error("User {} hasn't borrowed book {}", userId, book.getTitle());
            throw new IllegalArgumentException("You cannot return a book you haven't borrowed.");
        }
    }

    private void deactivateBorrowTransaction(Long userId, Long bookId) {
        Transaction activeBorrow = transactionRepository.findFirstByUserIdAndBookIdAndActionAndIsActive(
                userId, bookId, ActionType.BORROW, true);
        if (activeBorrow != null) {
            activeBorrow.setActive(false);
            transactionRepository.save(activeBorrow);
        }
    }
}
