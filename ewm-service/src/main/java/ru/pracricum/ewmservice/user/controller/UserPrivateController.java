package ru.pracricum.ewmservice.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.user.dto.UserDto;
import ru.pracricum.ewmservice.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/valuer/{valuerId}")
@Slf4j
public class UserPrivateController {

/*    private final UserService userService;

    @PutMapping(path = "/likes")
    public void putLikes(
            @PathVariable Long userId,
            @PathVariable Long valuerId) {
        userService.putLikes(userId, valuerId);
    }

    @DeleteMapping(path = "/likes")
    public void deleteLikes(
            @PathVariable Long userId,
            @PathVariable Long valuerId) {
        userService.deleteLikes(userId, valuerId);
    }*/

}
