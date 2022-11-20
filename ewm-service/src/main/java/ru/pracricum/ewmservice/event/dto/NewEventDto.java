package ru.pracricum.ewmservice.event.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.pracricum.ewmservice.event.model.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {

    String annotation;

    Long category;

    String description;

    LocalDateTime eventData;

    Location location;

    Boolean paid;

    Integer participantLimit;

    LocalDateTime requestModeration;

    String title;
}
