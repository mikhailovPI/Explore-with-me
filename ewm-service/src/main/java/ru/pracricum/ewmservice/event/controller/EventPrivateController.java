package ru.pracricum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.event.dto.EventFullDto;
import ru.pracricum.ewmservice.event.dto.EventShortDto;
import ru.pracricum.ewmservice.event.dto.NewEventDto;
import ru.pracricum.ewmservice.event.dto.UpdateEventRequest;
import ru.pracricum.ewmservice.event.service.EventService;
import ru.pracricum.ewmservice.requests.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
@Slf4j
@Validated
public class EventPrivateController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsByUser(
            @PathVariable Long userId,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("URL: /users/{userId}/events. GetMapping/Получение побытий текущего пользователя/getEventsByUser");
        return eventService.getEventsByUser(userId, from, size);
    }

    @GetMapping(path = "/{eventId}")
    public EventFullDto getEventByIdForUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("URL: /users/{userId}/events/{eventId}. GetMapping/Получение полной информации о события " +
                eventId + "/getEventByIdForUser");
        return eventService.getEventByIdForUser(userId, eventId);
    }

    @GetMapping(path = "/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsParticipationInEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("URL: /users/{userId}/events/{eventId}/requests. " +
                "GetMapping/Получение информации о запросах на участие в событии "
                + eventId + "/getRequestsParticipationInEvent");
        return eventService.getRequestsParticipationInEvent(userId, eventId);
    }

    @PostMapping
    public EventFullDto createEvent(
            @PathVariable Long userId,
            @RequestBody NewEventDto newEventDto) {
        log.info("URL: /users/{userId}/events. PostMapping/Создание события " + newEventDto + "/createEvent");
        return eventService.createEvent(userId, newEventDto);
    }

    @PatchMapping
    public EventFullDto updateEventByUser(
            @PathVariable Long userId,
            @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("URL: /users/{userId}/events. PatchMapping/Изменение события пользователя "
                + userId + "/updateEventByUser");
        return eventService.updateEventByUser(userId, updateEventRequest);
    }

    @PatchMapping(path = "/{eventId}")
    public EventFullDto cancelEventForUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("URL: /users/{userId}/events. PatchMapping/Отмена события " + eventId +
                " пользователя " + userId + "/cancelEventForUser");
        return eventService.cancelEventForUser(userId, eventId);
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequestsParticipationForEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId) {
        log.info("URL: /users/{userId}/events/{eventId}/requests/{reqId}/confirm." +
                " PatchMapping/Подтверждение заявки" + reqId +
                " события " + eventId +
                " пользователя " + userId +
                "/confirmRequestsParticipationForEvent");
        return eventService.confirmRequestsParticipationForEvent(userId, eventId, reqId);
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequestsParticipationForEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId) {
        log.info("URL: /users/{userId}/events/{eventId}/requests/{reqId}/reject." +
                " PatchMapping/Отклонение заявки" + reqId +
                " события " + eventId +
                " пользователя " + userId +
                "/rejectRequestsParticipationForEvent");
        return eventService.rejectRequestsParticipationForEvent(userId, eventId, reqId);
    }
}
