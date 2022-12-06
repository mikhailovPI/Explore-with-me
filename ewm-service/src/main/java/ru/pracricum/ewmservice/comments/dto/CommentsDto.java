package ru.pracricum.ewmservice.comments.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.pracricum.ewmservice.event.dto.EventCommentsDto;
import ru.pracricum.ewmservice.event.dto.EventShortDto;
import ru.pracricum.ewmservice.user.dto.UserDto;
import ru.pracricum.ewmservice.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentsDto {

    Long id;

    String text;

    Long user;
    //UserShortDto user;

    Long event;
    //EventCommentsDto event;

    LocalDateTime publishedOn;
}
