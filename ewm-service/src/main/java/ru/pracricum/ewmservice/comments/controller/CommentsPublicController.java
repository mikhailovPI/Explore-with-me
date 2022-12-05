package ru.pracricum.ewmservice.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.service.CommentsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentsPublicController {

    private final CommentsService commentsService;

    private final static String URL_COMMENT_PUBLIC = "/events/{eventId}/comments";

    @GetMapping(path = URL_COMMENT_PUBLIC)
    public List<CommentsDto> getComments(
            @PathVariable Long eventId,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("URL: /events/{eventId}/comments. GetMapping/Получение всех комментарием события " + eventId
                + "/getComments");
        return commentsService.getComments(eventId, from, size);
    }

    @GetMapping(path = URL_COMMENT_PUBLIC + "/{commentId}")
    public CommentsDto getCommentById(
            @PathVariable Long eventId,
            @PathVariable Long commentId) {
        log.info("URL: /events/{eventId}/comments/{commentId}. " +
                "GetMapping/Получение комментария " + commentId +
                " события " + eventId
                + "/getCommentById");
        return commentsService.getCommentById(eventId, commentId);
    }

    @GetMapping(path = "/user/{userId}/comments")
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
