package ru.pracricum.ewmservice.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.service.CommentsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events/{eventId}/comments")
@Slf4j
public class CommentsPublicController {

    private final CommentsService commentsService;

    @GetMapping
    public List<CommentsDto> getComments(
            @PathVariable Long eventId,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("URL: /events/{eventId}/comments. GetMapping/Получение всех комментарием события " + eventId
                + "/getComments");
        return commentsService.getComments(eventId, from, size);
    }

    @GetMapping(path = "/{commentId}")
    public CommentsDto getCommentById(
            @PathVariable Long eventId,
            @PathVariable Long commentId) {
        log.info("URL: /events/{eventId}/comments/{commentId}. " +
                "GetMapping/Получение комментария " + commentId +
                " события " + eventId
                + "/getCommentById");
        return commentsService.getCommentById(eventId, commentId);
    }


}
