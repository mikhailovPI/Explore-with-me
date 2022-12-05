package ru.pracricum.ewmservice.user.mapper;

import ru.pracricum.ewmservice.user.dto.NewUserRequest;
import ru.pracricum.ewmservice.user.dto.UserDto;
import ru.pracricum.ewmservice.user.model.User;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
                //user.getLikes(),
                //user.getDislikes()
                 );
    }

    public static User toUserNew(NewUserRequest newUserRequest) {
        return new User(
                null,
                newUserRequest.getName(),
                newUserRequest.getEmail()
                //0L,
//                0L
                );
    }


}
