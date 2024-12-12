package org.example.library.mapper;

import java.util.List;

import org.example.library.dto.request.transaction.CreateTransactionRequest;
import org.example.library.dto.response.transaction.GetTransactionResponse;
import org.example.library.entity.Book;
import org.example.library.entity.Transaction;
import org.example.library.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "book", source = "book")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    Transaction toEntity(CreateTransactionRequest request, User user, Book book);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "date", source = "date", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "username", source = "user.username")
    GetTransactionResponse toResponseDto(Transaction transaction);

    List<GetTransactionResponse> toResponseDtoList(List<Transaction> transactions);
}
