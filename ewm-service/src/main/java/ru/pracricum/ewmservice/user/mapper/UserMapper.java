package ru.pracricum.ewmservice.user.mapper;

import ru.pracricum.ewmservice.user.dto.UserDto;
import ru.pracricum.ewmservice.user.dto.UserShortDto;
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

    public static User toUserShortDto(UserShortDto userShortDto) {
        return new User(
                userShortDto.getId(),
                userShortDto.getName(),
                null);
    }

    public static UserShortDto toUserShort(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName());
    }
}
