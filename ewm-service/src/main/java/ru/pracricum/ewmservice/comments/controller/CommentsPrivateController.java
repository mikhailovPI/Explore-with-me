package ru.pracricum.ewmservice.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.service.CommentsService;

import static ru.pracricum.ewmservice.comments.controller.CommentsPrivateController.URL_PRIVATE_COMMENTS;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = URL_PRIVATE_COMMENTS)
@Slf4j
public class CommentsPrivateController {

    public final static String URL_PRIVATE_COMMENTS = "/events/{eventId}/comments";

    private final CommentsService commentsService;

    @PostMapping
    public CommentsDto createComment(
            @PathVariable Long eventId,
            @RequestBody CommentsDto commentsDto) {
        log.info("URL: " + URL_PRIVATE_COMMENTS + ". " +
                " PostMapping/Создание комментария " + commentsDto + "/createComment");
        return commentsService.createdComment(eventId, commentsDto);
    }

    @PatchMapping(path = "/{commentId}/users/{userId}")
    public CommentsDto patchComment(
            @PathVariable Long eventId,
            @PathVariable Long commentId,
            @PathVariable Long userId,
            @RequestBody CommentsDto commentsDto) {
        log.info("URL: " + URL_PRIVATE_COMMENTS + "{commentId}." +
                " PatchMapping/Обновления комментария " + commentId + "/patchComment");
        return commentsService.patchComment(eventId, commentId, userId, commentsDto);
    }
}
