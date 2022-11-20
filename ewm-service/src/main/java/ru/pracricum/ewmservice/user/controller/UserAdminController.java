package ru.pracricum.ewmservice.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.user.dto.UserDto;
import ru.pracricum.ewmservice.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
@Slf4j
public class UserAdminController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsersList(
            @RequestParam(name = "ids", required = false) List<Long> ids,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        log.info("URL: /users. GetMapping/Получение всех пользователей");
        return userService.getUsersList(ids, from, size);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("URL: /users. PostMapping/Создание пользователя");
        return userService.createUser(userDto);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        log.info("URL: /users/{userId}. DeleteMapping/Удаление пользователя с id: " + userId);
        userService.deleteUserById(userId);
    }
}
