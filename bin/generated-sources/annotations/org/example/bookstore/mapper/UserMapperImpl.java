package org.example.bookstore.mapper;

import javax.annotation.processing.Generated;
import org.example.bookstore.dto.request.auth.SignupRequest;
import org.example.bookstore.dto.request.user.UpdateUserRequest;
import org.example.bookstore.dto.request.user.UserRequest;
import org.example.bookstore.dto.response.user.UserResponse;
import org.example.bookstore.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-28T11:00:11+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240620-1855, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(SignupRequest createUserRequestDTO) {
        if ( createUserRequestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( createUserRequestDTO.getEmail() );
        user.setPassword( createUserRequestDTO.getPassword() );
        user.setUsername( createUserRequestDTO.getUsername() );

        return user;
    }

    @Override
    public User toEntity(UpdateUserRequest updateUserRequestDTO) {
        if ( updateUserRequestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( updateUserRequestDTO.getEmail() );
        user.setPassword( updateUserRequestDTO.getPassword() );
        user.setUsername( updateUserRequestDTO.getUsername() );

        return user;
    }

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userRequest.getEmail() );
        user.setPassword( userRequest.getPassword() );
        user.setUsername( userRequest.getUsername() );

        return user;
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setRoles( roleToStringSet( user.getRoles() ) );
        userResponse.setEmail( user.getEmail() );
        userResponse.setId( user.getId() );
        userResponse.setUsername( user.getUsername() );

        return userResponse;
    }
}
