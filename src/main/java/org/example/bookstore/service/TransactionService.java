package org.example.bookstore.service;

import java.time.LocalDateTime;
import java.util.List;

import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Transaction;
import org.example.bookstore.entity.User;
import org.example.bookstore.entity.enums.ActionType;
import org.example.bookstore.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public void recordTransaction(Long userId, Book book, ActionType action) {
        User user = userService.getUserById(userId);

        boolean hasActiveBorrow = transactionRepository.existsByUserIdAndBookIdAndActionAndIsActive(
                userId, book.getId(), ActionType.BORROW, true);

        if (action == ActionType.BORROW && hasActiveBorrow) {
            throw new IllegalArgumentException("You already have an active borrow transaction for this book.");
        }

        if (action == ActionType.RETURN && !hasActiveBorrow) {
            throw new IllegalArgumentException("You cannot return a book you haven't borrowed.");
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setAction(action);
        transaction.setDate(LocalDateTime.now());

        if (action == ActionType.RETURN) {
            Transaction activeBorrow = transactionRepository.findFirstByUserIdAndBookIdAndActionAndIsActive(
                    userId, book.getId(), ActionType.BORROW, true);
            activeBorrow.setActive(false);
            transactionRepository.save(activeBorrow);
        } else if (action == ActionType.BORROW) {
            transaction.setActive(true);
        }

        transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + transactionId));
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findAllByUserId(userId);
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
}
