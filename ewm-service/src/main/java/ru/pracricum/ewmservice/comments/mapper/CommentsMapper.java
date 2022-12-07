package ru.pracricum.ewmservice.comments.mapper;

import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.model.Comments;

public class CommentsMapper {

    public static CommentsDto toCommentDto(Comments comments) {
        return new CommentsDto(
                comments.getId(),
                comments.getText(),
                comments.getUser().getId(),
                comments.getEvent().getId(),
                comments.getPublishedOn());
    }

    public static Comments toComment(CommentsDto commentsDto) {
        return new Comments(
                commentsDto.getId(),
                commentsDto.getText(),
                null,
                null,
                commentsDto.getPublishedOn());
    }
}
