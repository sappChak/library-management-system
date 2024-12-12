package org.example.library.mapper;

import java.util.List;

import org.example.library.dto.request.book.CreateBookRequest;
import org.example.library.dto.request.book.UpdateBookRequest;
import org.example.library.dto.response.book.GetBookResponse;
import org.example.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book toEntity(CreateBookRequest createBookRequestDTO);

    @Mapping(target = "id", source = "book.id")
    GetBookResponse toResponseDto(Book book);

    List<GetBookResponse> toResponseDtoList(List<Book> books);

    @Mapping(target = "id", ignore = true)
    Book toEntity(UpdateBookRequest updateBookRequestDTO);

    default Page<GetBookResponse> toResponseDtoPage(Page<Book> books) {
        return books.map(this::toResponseDto);
    }

}
