package ma.petpulse.petpulsecore.service.mappers;

import ma.petpulse.petpulsecore.service.dtos.UserDto;
import org.mapstruct.Mapper;
import ma.petpulse.petpulsecore.dao.entities.User;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    List<UserDto> usersToUserDtos(List<User> users);
}