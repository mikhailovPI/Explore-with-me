package ru.pracricum.ewmservice.comments.mapper;

import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.model.Comments;
import ru.pracricum.ewmservice.event.dto.EventCommentsDto;
import ru.pracricum.ewmservice.user.dto.UserShortDto;

public class CommentsMapper {

    public static CommentsDto toCommentDto(Comments comments) {
        return new CommentsDto(
                comments.getId(),
                comments.getText(),
                comments.getUser().getId(),
                comments.getEvent().getId(),
                comments.getPublishedOn());
    }

//    public static CommentsDto toCommentDto(Comments comments) {
//        return new CommentsDto(
//                comments.getId(),
//                comments.getText(),
//                new UserShortDto(
//                        comments.getUser().getId(),
//                        comments.getUser().getName()),
//                new EventCommentsDto(
//                        comments.getEvent().getId(),
//                        comments.getEvent().getAnnotation(),
//                        new CategoriesDto(
//                                comments.getEvent().getCategories().getId(),
//                                comments.getEvent().getCategories().getName()),
//                        new UserShortDto(
//                                comments.getEvent().getInitiator().getId(),
//                                comments.getEvent().getInitiator().getName()),
//                        comments.getEvent().getTitle()),
//                comments.getPublishedOn());
//    }

    public static Comments toComment(CommentsDto commentsDto) {
        return new Comments(
                commentsDto.getId(),
                commentsDto.getText(),
                null,
                null,
                commentsDto.getPublishedOn());
    }
}
