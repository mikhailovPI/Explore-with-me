package ru.pracricum.ewmservice.event.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.event.model.Location;
import ru.pracricum.ewmservice.user.dto.UserShortDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class EventCommentsDto {

    Long id;
    String annotation;

    CategoriesDto category;

    UserShortDto initiator;

    String title;
}
