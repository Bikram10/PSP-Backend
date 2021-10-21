package com.bikram.appliedproject.service.mapper;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.service.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User DtoToUser(UserDto userDto);

    UserDto userToDTO(User user);
}
