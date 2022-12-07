package ru.pracricum.ewmservice.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.comments.service.CommentsService;

import static ru.pracricum.ewmservice.comments.controller.CommentsAdminController.URL_ADMIN_COMMENTS;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = URL_ADMIN_COMMENTS)
@Slf4j
public class CommentsAdminController {

    private final CommentsService commentsService;
    public final static String URL_ADMIN_COMMENTS = "/events/{eventId}/comments";

    @DeleteMapping
    public void deleteComments(@PathVariable Long eventId) {
        log.info("URL: " + URL_ADMIN_COMMENTS + ". " +
                "DeleteMapping/Удаление всех комментариев у события " + eventId + "/deleteComments");
        commentsService.deleteComments(eventId);
    }

    @DeleteMapping(path = "/{commentId}")
    public void deleteCommentById(
            @PathVariable Long eventId,
            @PathVariable Long commentId) {
        log.info("URL: " + URL_ADMIN_COMMENTS + "/{commentId}. " +
                "DeleteMapping/Удаления комментария " + commentId + "/deleteCommentById");
        commentsService.deleteCommentById(eventId, commentId);
    }
}
