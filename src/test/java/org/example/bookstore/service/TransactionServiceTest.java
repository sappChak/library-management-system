package org.example.bookstore.service;

import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Transaction;
import org.example.bookstore.entity.User;
import org.example.bookstore.entity.enums.ActionType;
import org.example.bookstore.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TransactionService transactionService;

    private User user;
    private Book book;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("andrii_konotop");

        book = new Book();
        book.setId(101L);
        book.setTitle("Test Book");

        transaction = new Transaction();
        transaction.setId(1001L);
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setAction(ActionType.BORROW);
        transaction.setDate(LocalDateTime.now());
        transaction.setActive(true);
    }

    @Test
    void shouldAddBorrowTransactionSuccessfully() {
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(transactionRepository.existsByUserIdAndBookIdAndActionAndIsActive(
                user.getId(), book.getId(), ActionType.BORROW, true)).thenReturn(false);

        transactionService.recordTransaction(user.getId(), book, ActionType.BORROW);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionCaptor.capture());

        Transaction savedTransaction = transactionCaptor.getValue();
        assertThat(savedTransaction.getUser()).isEqualTo(user);
        assertThat(savedTransaction.getBook()).isEqualTo(book);
        assertThat(savedTransaction.getAction()).isEqualTo(ActionType.BORROW);
        assertThat(savedTransaction.isActive()).isTrue();
        assertThat(savedTransaction.getDate()).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateBorrowTransaction() {
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(transactionRepository.existsByUserIdAndBookIdAndActionAndIsActive(
                user.getId(), book.getId(), ActionType.BORROW, true)).thenReturn(true);

        assertThatThrownBy(() -> transactionService.recordTransaction(user.getId(), book, ActionType.BORROW))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("You already have an active borrow transaction for this book.");

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldAddReturnTransactionSuccessfully() {
        // Arrange
        Transaction activeBorrowTransaction = new Transaction();
        activeBorrowTransaction.setId(2002L);
        activeBorrowTransaction.setUser(user);
        activeBorrowTransaction.setBook(book);
        activeBorrowTransaction.setAction(ActionType.BORROW);
        activeBorrowTransaction.setActive(true);

        when(userService.getUserById(user.getId())).thenReturn(user);
        when(transactionRepository.existsByUserIdAndBookIdAndActionAndIsActive(
                user.getId(), book.getId(), ActionType.BORROW, true)).thenReturn(true);
        when(transactionRepository.findFirstByUserIdAndBookIdAndActionAndIsActive(
                user.getId(), book.getId(), ActionType.BORROW, true)).thenReturn(activeBorrowTransaction);

        // Act
        transactionService.recordTransaction(user.getId(), book, ActionType.RETURN);

        // Assert
        // Verify the first save: marking the active borrow transaction as inactive
        assertThat(activeBorrowTransaction.isActive()).isFalse();
        verify(transactionRepository).save(activeBorrowTransaction);

        // Verify the second save: creating the new RETURN transaction
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(2)).save(transactionCaptor.capture());

        List<Transaction> savedTransactions = transactionCaptor.getAllValues();
        Transaction savedReturnTransaction = savedTransactions.get(1);

        assertThat(savedReturnTransaction.getUser()).isEqualTo(user);
        assertThat(savedReturnTransaction.getBook()).isEqualTo(book);
        assertThat(savedReturnTransaction.getAction()).isEqualTo(ActionType.RETURN);
        assertThat(savedReturnTransaction.isActive()).isFalse();
        assertThat(savedReturnTransaction.getDate()).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenReturningNonBorrowedBook() {
        when(userService.getUserById(user.getId())).thenReturn(user);
        when(transactionRepository.existsByUserIdAndBookIdAndActionAndIsActive(
                user.getId(), book.getId(), ActionType.BORROW, true)).thenReturn(false);

        assertThatThrownBy(() -> transactionService.recordTransaction(user.getId(), book, ActionType.RETURN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("You cannot return a book you haven't borrowed.");

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldGetTransactionById() {
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.getTransactionById(transaction.getId());

        assertThat(foundTransaction).isEqualTo(transaction);
    }

    @Test
    void shouldThrowExceptionWhenTransactionNotFoundById() {
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.getTransactionById(transaction.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Transaction not found with ID: " + transaction.getId());
    }

    @Test
    void shouldGetTransactionsByUserId() {
        when(transactionRepository.findAllByUserId(user.getId())).thenReturn(List.of(transaction));

        List<Transaction> transactions = transactionService.getTransactionsByUserId(user.getId());

        assertThat(transactions).containsExactly(transaction);
    }

    @Test
    void shouldGetAllTransactions() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<Transaction> transactions = transactionService.getAllTransactions();

        assertThat(transactions).containsExactly(transaction);
    }

    @Test
    void shouldDeleteTransactionSuccessfully() {
        when(transactionRepository.existsById(transaction.getId())).thenReturn(true);

        transactionService.deleteTransaction(transaction.getId());

        verify(transactionRepository).deleteById(transaction.getId());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTransaction() {
        when(transactionRepository.existsById(transaction.getId())).thenReturn(false);

        assertThatThrownBy(() -> transactionService.deleteTransaction(transaction.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Transaction not found with ID: " + transaction.getId());

        verify(transactionRepository, never()).deleteById(transaction.getId());
    }
}
