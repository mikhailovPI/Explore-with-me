package ru.pracricum.ewmservice.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.service.CommentsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentsPublicController {

    private final CommentsService commentsService;

    public static final String URL_PUBLIC_COMMENTS_USER = "/user/{userId}/comments";
    public static final String URL_PUBLIC_COMMENTS = "/events/{eventId}/comments";


    @GetMapping(path = URL_PUBLIC_COMMENTS)
    public List<CommentsDto> getComments(
            @PathVariable Long eventId,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("URL: " + URL_PUBLIC_COMMENTS + ". GetMapping/Получение всех комментарием события " + eventId
                + "/getComments");
        return commentsService.getComments(eventId, from, size);
    }

    @GetMapping(path = URL_PUBLIC_COMMENTS + "/{commentId}")
    public CommentsDto getCommentById(
            @PathVariable Long eventId,
            @PathVariable Long commentId) {
        log.info("URL: " + URL_PUBLIC_COMMENTS + "/{commentId}. " +
                "GetMapping/Получение комментария " + commentId +
                " события " + eventId
                + "/getCommentById");
        return commentsService.getCommentById(eventId, commentId);
    }

    @GetMapping(path = URL_PUBLIC_COMMENTS_USER)
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
