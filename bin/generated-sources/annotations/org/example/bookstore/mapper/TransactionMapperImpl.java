package org.example.bookstore.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.bookstore.dto.request.transaction.CreateTransactionRequest;
import org.example.bookstore.dto.response.transaction.GetTransactionResponse;
import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Transaction;
import org.example.bookstore.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-25T18:24:22+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240620-1855, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toEntity(CreateTransactionRequest request, User user, Book book) {
        if ( request == null && user == null && book == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        if ( request != null ) {
            transaction.setAction( request.getAction() );
        }
        transaction.setUser( user );
        transaction.setBook( book );
        transaction.setDate( java.time.LocalDateTime.now() );

        return transaction;
    }

    @Override
    public GetTransactionResponse toResponseDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        GetTransactionResponse getTransactionResponse = new GetTransactionResponse();

        getTransactionResponse.setUserId( transactionUserId( transaction ) );
        getTransactionResponse.setBookId( transactionBookId( transaction ) );
        getTransactionResponse.setDate( transaction.getDate() );
        getTransactionResponse.setBookTitle( transactionBookTitle( transaction ) );
        getTransactionResponse.setUserName( transactionUserUsername( transaction ) );
        if ( transaction.getAction() != null ) {
            getTransactionResponse.setAction( transaction.getAction().name() );
        }
        getTransactionResponse.setTransactionId( transaction.getTransactionId() );

        return getTransactionResponse;
    }

    @Override
    public List<GetTransactionResponse> toResponseDtoList(List<Transaction> transactions) {
        if ( transactions == null ) {
            return null;
        }

        List<GetTransactionResponse> list = new ArrayList<GetTransactionResponse>( transactions.size() );
        for ( Transaction transaction : transactions ) {
            list.add( toResponseDto( transaction ) );
        }

        return list;
    }

    private Long transactionUserId(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }
        User user = transaction.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long transactionBookId(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }
        Book book = transaction.getBook();
        if ( book == null ) {
            return null;
        }
        Long id = book.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String transactionBookTitle(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }
        Book book = transaction.getBook();
        if ( book == null ) {
            return null;
        }
        String title = book.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }

    private String transactionUserUsername(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }
        User user = transaction.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
