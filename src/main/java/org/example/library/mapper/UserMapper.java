package org.example.library.mapper;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.example.library.dto.request.auth.SignupRequest;
import org.example.library.dto.request.user.CreateUserRequest;
import org.example.library.dto.request.user.UpdateUserRequest;
import org.example.library.dto.response.user.CreateUserResponse;
import org.example.library.entity.Role;
import org.example.library.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(SignupRequest createUserRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(CreateUserRequest userRequest);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleToStringSet")
    CreateUserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromRequest(UpdateUserRequest userRequest, @MappingTarget User user);

    @Named("roleToStringSet")
    default Set<String> roleToStringSet(Collection<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
