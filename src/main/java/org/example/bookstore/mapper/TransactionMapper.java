package org.example.bookstore.mapper;

import java.util.List;

import org.example.bookstore.dto.request.transaction.CreateTransactionRequest;
import org.example.bookstore.dto.response.transaction.CreateTransactionResponse;
import org.example.bookstore.dto.response.transaction.GetTransactionResponse;
import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Transaction;
import org.example.bookstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "book", source = "book")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    Transaction toEntity(CreateTransactionRequest request, User user, Book book);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookId", source = "book.id")
    CreateTransactionResponse toCreateResponseDto(Transaction transaction);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "date", source = "date", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "userName", source = "user.username")
    GetTransactionResponse toGetResponseDto(Transaction transaction);

    List<GetTransactionResponse> toGetResponseDtoList(List<Transaction> transactions);
}
