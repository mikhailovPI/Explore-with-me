package ru.pracricum.ewmservice.requests.mapper;

import ru.pracricum.ewmservice.requests.dto.*;
import ru.pracricum.ewmservice.requests.model.Requests;

public class RequestMapper {

    public static Requests toRequest (ParticipationRequestDto participationRequestDto) {
        return new Requests(
                participationRequestDto.getId(),
                null,
                null,
                participationRequestDto.getCreated(),
                participationRequestDto.getState());
    }

    public static ParticipationRequestDto toRequestDto (Requests requests) {
        return new ParticipationRequestDto(
                requests.getId(),
                requests.getCreated(),
                requests.getEvent().getId(),
                requests.getId(),
                requests.getStatus());
    }
}
