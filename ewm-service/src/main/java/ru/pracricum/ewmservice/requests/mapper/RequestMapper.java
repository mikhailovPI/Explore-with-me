package ru.pracricum.ewmservice.requests.mapper;

import ru.pracricum.ewmservice.requests.dto.ParticipationRequestDto;
import ru.pracricum.ewmservice.requests.model.Requests;

public class RequestMapper {

    public static Requests toRequest(ParticipationRequestDto participationRequestDto) {
        return new Requests(
                participationRequestDto.getId(),
                participationRequestDto.getEvent(),
                participationRequestDto.getRequester(),
                participationRequestDto.getCreated(),
                participationRequestDto.getStatus());
    }

    public static ParticipationRequestDto toRequestDto(Requests requests) {
        return new ParticipationRequestDto(
                requests.getId(),
                requests.getCreated(),
                requests.getEvent(),
                requests.getId(),
                requests.getStatus());
    }
}
