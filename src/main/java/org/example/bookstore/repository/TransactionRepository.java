package org.example.bookstore.repository;

import java.util.List;

import org.example.bookstore.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUserId(Long userId);

    List<Transaction> findAllByBookId(Long bookId);
}
