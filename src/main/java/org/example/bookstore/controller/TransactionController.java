package org.example.bookstore.controller;

import java.util.List;

import org.example.bookstore.dto.response.transaction.GetTransactionResponse;
import org.example.bookstore.entity.User;
import org.example.bookstore.mapper.TransactionMapper;
import org.example.bookstore.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Endpoints for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Operation(summary = "Get all transactions", description = "Retrieve a list of all transactions.")
    @GetMapping
    public ResponseEntity<List<GetTransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionMapper.toResponseDtoList(transactionService.getAllTransactions()));
    }

    @Operation(summary = "Get transactions by user ID", description = "Retrieve a list of transactions by user.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetTransactionResponse>> getTransactionsByUserId(@PathVariable Long userId) {
        return ResponseEntity
                .ok(transactionMapper.toResponseDtoList(transactionService.getTransactionsByUserId(userId)));
    }

    @Operation(summary = "Get transactions by current user", description = "Retrieve a list of transactions by authenticated user.")
    @GetMapping("/me")
    public ResponseEntity<List<GetTransactionResponse>> getTransactionsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            userId = userDetails.getId();
        }
        return ResponseEntity
                .ok(transactionMapper.toResponseDtoList(transactionService.getTransactionsByUserId(userId)));
    }

    @Operation(summary = "Get number of active borrowings", description = "Retrieve a number of active borrowings.")
    @GetMapping("/active/count")
    public ResponseEntity<Long> getActiveBorrowingsCount() {
        return ResponseEntity.ok(transactionService.getActiveBorrowingsCount());
    }

    @Operation(summary = "Get total number of returns", description = "Retrieve a total number of returns.")
    @GetMapping("/returned/count")
    public ResponseEntity<Long> getActiveReturnsCount() {
        return ResponseEntity.ok(transactionService.getTotalReturnedBooksCount());
    }

    @Operation(summary = "Delete a transaction", description = "Delete atransaction byits ID.")
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }

}
