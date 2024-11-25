package org.example.bookstore.controller;

import java.util.List;

import org.example.bookstore.dto.request.transaction.CreateTransactionRequest;
import org.example.bookstore.dto.response.transaction.CreateTransactionResponse;
import org.example.bookstore.dto.response.transaction.GetTransactionResponse;
import org.example.bookstore.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Endpoints for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create a new transaction", description = "Record a new transaction (e.g., borrowing or returning a book).")
    public ResponseEntity<CreateTransactionResponse> addTransaction(
            @RequestBody @Valid CreateTransactionRequest createTransactionRequestDTO) {
        CreateTransactionResponse createdTransaction = transactionService
                .addTransaction(createTransactionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Get a transaction by ID", description = "Retrieve details of a specific transaction by its ID.")
    public ResponseEntity<GetTransactionResponse> getTransactionById(@PathVariable Long transactionId) {
        GetTransactionResponse transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieve a list of all transactions.")
    public ResponseEntity<List<GetTransactionResponse>> getAllTransactions() {
        List<GetTransactionResponse> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/{transactionId}")
    @Operation(summary = "Delete a transaction", description = "Delete a transaction by its ID.")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
