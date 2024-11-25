package org.example.bookstore.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.bookstore.dto.request.book.CreateBookRequest;
import org.example.bookstore.dto.request.book.UpdateBookRequest;
import org.example.bookstore.dto.response.book.GetBookResponse;
import org.example.bookstore.entity.Book;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-25T18:24:22+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240620-1855, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toEntity(CreateBookRequest createBookRequestDTO) {
        if ( createBookRequestDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setAuthor( createBookRequestDTO.getAuthor() );
        book.setAvailableCopies( createBookRequestDTO.getAvailableCopies() );
        book.setIsbn( createBookRequestDTO.getIsbn() );
        book.setTitle( createBookRequestDTO.getTitle() );

        return book;
    }

    @Override
    public GetBookResponse toResponseDto(Book book) {
        if ( book == null ) {
            return null;
        }

        GetBookResponse getBookResponse = new GetBookResponse();

        getBookResponse.setBookId( book.getId() );
        getBookResponse.setAuthor( book.getAuthor() );
        getBookResponse.setAvailableCopies( book.getAvailableCopies() );
        getBookResponse.setIsbn( book.getIsbn() );
        getBookResponse.setTitle( book.getTitle() );

        return getBookResponse;
    }

    @Override
    public List<GetBookResponse> toResponseDtoList(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<GetBookResponse> list = new ArrayList<GetBookResponse>( books.size() );
        for ( Book book : books ) {
            list.add( toResponseDto( book ) );
        }

        return list;
    }

    @Override
    public Book toEntity(UpdateBookRequest updateBookRequestDTO) {
        if ( updateBookRequestDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setAuthor( updateBookRequestDTO.getAuthor() );
        book.setAvailableCopies( updateBookRequestDTO.getAvailableCopies() );
        book.setIsbn( updateBookRequestDTO.getIsbn() );
        book.setTitle( updateBookRequestDTO.getTitle() );

        return book;
    }
}
