package org.example.bookstore.mapper;

import javax.annotation.processing.Generated;
import org.example.bookstore.dto.request.SignupRequest;
import org.example.bookstore.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-25T17:15:27+0100",
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
}
