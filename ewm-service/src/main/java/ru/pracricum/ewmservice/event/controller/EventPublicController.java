package ru.pracricum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.event.dto.EventFullDto;
import ru.pracricum.ewmservice.event.dto.EventShortDto;
import ru.pracricum.ewmservice.event.service.EventService;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
@Slf4j
public class EventPublicController {

    private final EventService eventService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public List<EventShortDto> getEvents(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "paid", required = false) Boolean paid,
            @RequestParam(name = "rangeStart",
                    defaultValue = "1980-01-01 13:30:38")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd",
                    defaultValue = "2050-01-01 00:00:00")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) throws ValidationException {
        log.info("URL: /events. PostMapping/Получение списка событий с фильтрами/getEvents");
        return eventService.getEvents(
                text,
                categories,
                paid,
                rangeStart
                /*LocalDateTime.parse(rangeStart, formatter)*/,
                rangeEnd
                /*LocalDateTime.parse(rangeEnd, formatter)*/,
                onlyAvailable,
                sort,
                from,
                size);
    }

    @GetMapping(path = "/{eventId}")
    public EventFullDto getEventById(
            @PathVariable Long eventId) {
        log.info("URL: /event/{eventId}. GetMapping/Получение события " + eventId + "/getEventById");
        return eventService.getEventById(eventId);
    }
}
