package org.example.bookstore.mapper;

import org.example.bookstore.dto.request.SignupRequest;
import org.example.bookstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(SignupRequest createUserRequestDTO);
}
