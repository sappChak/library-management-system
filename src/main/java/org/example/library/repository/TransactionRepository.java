package org.example.library.repository;

import java.util.List;

import org.example.library.entity.Transaction;
import org.example.library.entity.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByUserIdAndBookIdAndActionAndIsActive(Long userId, Long bookId, ActionType action, boolean isActive);

    Transaction findFirstByUserIdAndBookIdAndActionAndIsActive(Long userId, Long bookId, ActionType action,
            boolean isActive);

    List<Transaction> findAllByUserId(Long userId);

    List<Transaction> findAllByBookId(Long bookId);

    List<Transaction> findAllByUserIdAndIsActive(Long userId, boolean isActive);

    Long countByUserIdAndIsActive(Long userId, boolean isActive);

    Long countAllByActionAndIsActive(ActionType action, boolean isActive);
}
