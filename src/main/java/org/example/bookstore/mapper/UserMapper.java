package org.example.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.example.bookstore.dto.request.SignupRequest;
import org.example.bookstore.dto.request.user.UserRequest;
import org.example.bookstore.dto.response.user.UserResponse;
import org.example.bookstore.entity.Role;
import org.example.bookstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(SignupRequest createUserRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserRequest userRequest);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleToStringSet")
    UserResponse toResponse(User user);

    @Named("roleToStringSet")
    default Set<String> roleToStringSet(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
