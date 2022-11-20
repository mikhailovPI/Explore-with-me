package ru.pracricum.ewmservice.requests.mapper;

import ru.pracricum.ewmservice.requests.dto.*;
import ru.pracricum.ewmservice.requests.model.Requests;

public class RequestMapper {

    public static Requests toEventParticipation (ParticipationRequestDto participationRequestDto) {
        return new Requests();
    }

}
