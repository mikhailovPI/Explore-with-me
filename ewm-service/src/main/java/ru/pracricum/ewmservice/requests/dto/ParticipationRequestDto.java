package ru.pracricum.ewmservice.requests.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.pracricum.ewmservice.requests.model.ParticipationStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {

    Long id;

    LocalDateTime created;

    Long event;

    Long requester;

    ParticipationStatus status;

}
