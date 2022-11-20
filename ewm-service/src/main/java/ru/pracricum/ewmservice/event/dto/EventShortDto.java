package ru.pracricum.ewmservice.event.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {

        Long id;

        String annotation;

        CategoriesDto category;

        Integer conformedRequests;

        LocalDateTime eventData;

        UserShortDto initiator;

        Boolean paid;

        String title;

        Long views;
}
