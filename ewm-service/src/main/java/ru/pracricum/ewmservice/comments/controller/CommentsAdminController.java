package ru.pracricum.ewmservice.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.service.CommentsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user/{userId}/comments")
@Slf4j
public class CommentsAdminController {

    private final CommentsService commentsService;

    @GetMapping
    public List<CommentsDto> getCommentsByUser(
            @PathVariable Long userId,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("URL: /user/{userId}/comments. " +
                "GetMapping/Получение комментарие пользователя " + userId
                + "/getCommentsByUser");
        return commentsService.getCommentsByUser(userId, from, size);
    }
}
