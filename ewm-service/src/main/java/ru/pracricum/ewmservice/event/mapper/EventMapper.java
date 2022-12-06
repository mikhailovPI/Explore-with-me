package ru.pracricum.ewmservice.event.mapper;

import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.event.dto.EventFullDto;
import ru.pracricum.ewmservice.event.dto.EventShortDto;
import ru.pracricum.ewmservice.event.dto.NewEventDto;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.model.EventState;
import ru.pracricum.ewmservice.event.model.Location;
import ru.pracricum.ewmservice.user.dto.UserShortDto;

public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                new CategoriesDto(
                        event.getCategories().getId(),
                        event.getCategories().getName()),
                event.getConformedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                new UserShortDto(
                        event.getInitiator().getId(),
                        event.getInitiator().getName()),
                new Location(
                        event.getLat(),
                        event.getLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                null);
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                new CategoriesDto(
                        event.getCategories().getId(),
                        event.getCategories().getName()),
                event.getConformedRequests(),
                event.getEventDate(),
                new UserShortDto(
                        event.getInitiator().getId(),
                        event.getInitiator().getName()),
                event.getPaid(),
                event.getTitle(),
                null);
    }

    public static Event toEventNew(NewEventDto newEventDto) {
        return new Event(
                null,
                newEventDto.getAnnotation(),
                null,
                null,
                null,
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                null,
                newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                null,
                newEventDto.getRequestModeration(),
                EventState.PENDING,
                newEventDto.getTitle());
    }
}
