package ru.pracricum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.event.dto.EventFullDto;
import ru.pracricum.ewmservice.event.dto.EventShortDto;
import ru.pracricum.ewmservice.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
@Slf4j
public class EventPublicController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(
            @PathVariable String text,
            @RequestParam List<CategoriesDto> categories,
            @PathVariable Boolean paid,
            @PathVariable LocalDateTime rangeStart,
            @PathVariable LocalDateTime rangeEnd,
            @PathVariable Boolean onlyAvailable,
            @PathVariable String sort,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        log.info("URL: /events. PostMapping/Получение списка событий с фильтрами: " );
        return eventService.getEvents(
                text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size);
    }

    @GetMapping(path = "/{eventId}")
    public EventFullDto getEventById(
            @PathVariable Long eventId) {
        log.info("URL: /event/{eventId}. GetMapping/Получение события " +eventId);
        return eventService.getEventById(eventId);
    }
}
