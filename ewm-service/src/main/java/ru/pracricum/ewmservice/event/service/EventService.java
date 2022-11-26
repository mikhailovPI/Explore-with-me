package ru.pracricum.ewmservice.event.service;

import org.springframework.stereotype.Service;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.event.dto.*;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.model.EventState;
import ru.pracricum.ewmservice.requests.dto.ParticipationRequestDto;
import ru.pracricum.ewmservice.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface EventService {
    List<EventShortDto> getEvents(
            String text,
            List<CategoriesDto> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            String sort,
            int from,
            int size);

    EventFullDto getEventById(Long eventId);

    List<EventShortDto> getEventsByUser(Long userId, int from, int size);

    EventFullDto getEventByIdForUser(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestsParticipationInEvent(Long userId, Long eventId);

    EventFullDto createEvent(Long userId, NewEventDto eventFullDto);

    EventFullDto updateEventByUser(Long userId, UpdateEventRequest updateEventRequest);

    EventFullDto cancelEventForUser(Long userId, Long eventId);

    ParticipationRequestDto confirmRequestsParticipationForEvent(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequestsParticipationForEvent(Long userId, Long eventId, Long reqId);

    List<EventFullDto> searchEvents(List<UserDto> usersId, List<EventState> eventStates, List<CategoriesDto> categories, LocalDateTime startSearch, LocalDateTime endSearch, int from, int size);

    EventFullDto putEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    List<Event> getEventsByIds(List<Long> ids);
}
