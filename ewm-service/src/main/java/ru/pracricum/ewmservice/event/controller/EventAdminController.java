package ru.pracricum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.event.dto.AdminUpdateEventRequest;
import ru.pracricum.ewmservice.event.dto.EventFullDto;
import ru.pracricum.ewmservice.event.model.EventState;
import ru.pracricum.ewmservice.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
@Slf4j
public class EventAdminController {

    private final EventService eventService;

    private static final String URL_EVENT_ADMIN = "/{eventId}";

    @GetMapping
    public List<EventFullDto> searchEvents(
            @RequestParam(name = "users", required = false) List<Long> usersId,
            @RequestParam(name = "states", required = false) List<EventState> eventStates,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "rangeStart", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("URL: /admin/events. GetMapping/Поиск события по параметрам/searchEvents");
        return eventService.searchEvents(
                usersId,
                eventStates,
                categories,
                rangeStart,
                rangeEnd,
                from,
                size);
    }

    @PutMapping(path = URL_EVENT_ADMIN)
    public EventFullDto putEvent(
            @PathVariable Long eventId,
            @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("URL: /admin/events/{eventId}. PutMapping/Редактирование события " + eventId + "/putEvent");
        return eventService.putEvent(eventId, adminUpdateEventRequest);
    }

    @PatchMapping(path = URL_EVENT_ADMIN + "/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("URL: /admin/events/{eventId}/publish. PatchMapping/Публикация события " + eventId + "/publishEvent");
        return eventService.publishEvent(eventId);
    }

    @PatchMapping(path = URL_EVENT_ADMIN + "/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("URL: /admin/events/{eventId}/reject. PatchMapping/Отклонениния события " + eventId + "/rejectEvent");
        return eventService.rejectEvent(eventId);
    }
}
