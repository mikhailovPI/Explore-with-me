package ru.pracricum.ewmservice.user.mapper;

import ru.pracricum.ewmservice.user.dto.UserDto;
import ru.pracricum.ewmservice.user.model.User;

public class UserMapper {

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail());
    }
}
