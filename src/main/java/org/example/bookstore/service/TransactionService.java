package org.example.bookstore.service;

import java.time.LocalDateTime;
import java.util.List;

import org.example.bookstore.dto.request.transaction.CreateTransactionRequest;
import org.example.bookstore.dto.response.transaction.CreateTransactionResponse;
import org.example.bookstore.dto.response.transaction.GetTransactionResponse;
import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Transaction;
import org.example.bookstore.entity.User;
import org.example.bookstore.mapper.TransactionMapper;
import org.example.bookstore.repository.BookRepository;
import org.example.bookstore.repository.TransactionRepository;
import org.example.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final TransactionMapper transactionMapper;

    public CreateTransactionResponse addTransaction(CreateTransactionRequest createTransactionRequestDTO) {
        User user = userRepository.findById(createTransactionRequestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with ID: " + createTransactionRequestDTO.getUserId()));

        Book book = bookRepository.findById(createTransactionRequestDTO.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book not found with ID: " + createTransactionRequestDTO.getBookId()));

        Transaction transaction = transactionMapper.toEntity(createTransactionRequestDTO, user, book);

        return transactionMapper.toCreateResponseDto(transactionRepository.save(transaction));
    }

    public GetTransactionResponse getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + transactionId));

        return transactionMapper.toGetResponseDto(transaction);
    }

    public List<GetTransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionMapper.toGetResponseDtoList(transactions);
    }

    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new EntityNotFoundException("Transaction not found with ID: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }
}
