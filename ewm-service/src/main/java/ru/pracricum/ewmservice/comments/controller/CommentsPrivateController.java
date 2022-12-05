package ru.pracricum.ewmservice.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.service.CommentsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events/{eventId}/comments")
@Slf4j
public class CommentsPrivateController {

    private final CommentsService commentsService;

    @PostMapping
    public CommentsDto createComment(
            @PathVariable Long eventId,
            @RequestBody CommentsDto commentsDto) {
        log.info("URL: /events/{eventId}/comments. " +
                "PostMapping/Создание комментария " + commentsDto + "/createComment");
        return commentsService.createdComment(eventId, commentsDto);
    }

    @PatchMapping(path = "/{commentId}")
    public CommentsDto patchComment(
            @PathVariable Long eventId,
            @PathVariable Long commentId,
            @RequestBody CommentsDto commentsDto) {
        log.info("URL: /events/{eventId}/comments. " +
                "PatchMapping/Обновления комментария " + commentId + "/patchComment");
        return commentsService.patchComment(eventId, commentId, commentsDto);
    }

    @DeleteMapping
    public void deleteComments(@PathVariable Long eventId) {
        log.info("URL: /events/{eventId}/comments. " +
                "DeleteMapping/Удаление всех комментариев у события " + eventId + "/deleteComments");
        commentsService.deleteComments(eventId);
    }

    @DeleteMapping(path = "/{commentId}")
    public void deleteCommentById(
            @PathVariable Long eventId,
            @PathVariable Long commentId) {
        log.info("URL: /events/{eventId}/comments/{commentId}. " +
                "DeleteMapping/Удаления комментария " + commentId + "/deleteCommentById");
        commentsService.deleteCommentById(eventId, commentId);
    }

}
