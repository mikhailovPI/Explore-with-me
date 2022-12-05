package ru.pracricum.ewmservice.user.service;

import org.springframework.stereotype.Service;
import ru.pracricum.ewmservice.user.dto.NewUserRequest;
import ru.pracricum.ewmservice.user.dto.UserDto;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> getUsersList(List<Long> ids, int from, int size);

    UserDto createUser(NewUserRequest userDto);

    void deleteUserById(Long userId);

    void putLikes(Long userId, Long valuerId);
    void deleteLikes(Long userId, Long valuerId);
}
