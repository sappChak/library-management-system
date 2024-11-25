package org.example.bookstore.repository;

import java.util.List;

import org.example.bookstore.entity.Transaction;
import org.example.bookstore.entity.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByUserIdAndBookIdAndActionAndIsActive(Long userId, Long bookId, ActionType action, boolean isActive);

    Transaction findFirstByUserIdAndBookIdAndActionAndIsActive(Long userId, Long bookId, ActionType action,
            boolean isActive);

    List<Transaction> findAllByUserId(Long userId);

    List<Transaction> findAllByBookId(Long bookId);
}
