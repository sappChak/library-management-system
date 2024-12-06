package org.example.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.example.bookstore.dto.request.auth.SignupRequest;
import org.example.bookstore.dto.request.user.CreateUserRequest;
import org.example.bookstore.dto.request.user.UpdateUserRequest;
import org.example.bookstore.dto.response.user.CreateUserResponse;
import org.example.bookstore.entity.Role;
import org.example.bookstore.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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
    User toEntity(CreateUserRequest userRequest);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleToStringSet")
    CreateUserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromRequest(UpdateUserRequest userRequest, @MappingTarget User user);

    @Named("roleToStringSet")
    default Set<String> roleToStringSet(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
