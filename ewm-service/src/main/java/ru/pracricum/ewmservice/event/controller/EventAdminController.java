package ru.pracricum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.event.dto.AdminUpdateEventRequest;
import ru.pracricum.ewmservice.event.dto.EventFullDto;
import ru.pracricum.ewmservice.event.model.EventState;
import ru.pracricum.ewmservice.event.service.EventService;
import ru.pracricum.ewmservice.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
@Slf4j
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> searchEvents(
            @RequestParam List<UserDto> usersId,
            @RequestParam List<EventState> eventStates,
            @RequestParam List<CategoriesDto> categories,
            @PathVariable LocalDateTime rangeStart,
            @PathVariable LocalDateTime rangeEnd,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        log.info("URL: /admin/events. GetMapping/Поиск события по параметрам: " );
        return eventService.searchEvents(
                usersId,
                eventStates,
                categories,
                rangeStart,
                rangeEnd,
                from,
                size);
    }

    @PutMapping(path = "/{eventId}")
    public EventFullDto putEvent(
            @PathVariable Long eventId,
            @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("URL: /admin/events/{eventId}. PutMapping/Редактирование события " +eventId);
        return eventService.putEvent(eventId, adminUpdateEventRequest);
    }

    @PatchMapping(path = "/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("URL: /admin/events/{eventId}/publish. PatchMapping/Публикация события " +eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping(path = "/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("URL: /admin/events/{eventId}/reject. PatchMapping/Отклонениния события " +eventId);
        return eventService.rejectEvent(eventId);
    }
}
