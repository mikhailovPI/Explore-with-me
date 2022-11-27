package ru.pracricum.ewmservice.event.mapper;

import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.model.Categories;
import ru.pracricum.ewmservice.event.dto.*;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.model.EventState;
import ru.pracricum.ewmservice.event.model.Location;
import ru.pracricum.ewmservice.user.dto.UserShortDto;

public class EventMapper {

    public static Event toEvent(EventFullDto eventFullDto) {
        return new Event(
                eventFullDto.getId(),
                eventFullDto.getAnnotation(),
                null,
                eventFullDto.getConfirmedRequests(),
                eventFullDto.getCreatedOn(),
                eventFullDto.getDescription(),
                eventFullDto.getEventDate(),
                null,
                eventFullDto.getLocation().getLat(),
                eventFullDto.getLocation().getLon(),
                eventFullDto.getPaid(),
                eventFullDto.getParticipantLimit(),
                eventFullDto.getPublishedOn(),
                eventFullDto.getRequestModeration(),
                eventFullDto.getState(),
                eventFullDto.getTitle(),
                eventFullDto.getViews());
    }

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
                event.getViews());
    }

    public static Event toEventShort(EventShortDto eventShortDto) {
        return new Event(
                eventShortDto.getId(),
                eventShortDto.getAnnotation(),
                new Categories(
                        eventShortDto.getCategory().getId(),
                        eventShortDto.getCategory().getName()),
                eventShortDto.getConfirmedRequests(),
                null,
                null,
                eventShortDto.getEventDate(),
                null,
                null,
                null,
                eventShortDto.getPaid(),
                null,
                null,
                null,
                null,
                eventShortDto.getTitle(),
                eventShortDto.getViews());
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
                event.getViews());
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
                newEventDto.getTitle(),
                null);
    }

    public static NewEventDto toEventNewDto(Event event) {
        return new NewEventDto(
                event.getAnnotation(),
                event.getCategories().getId(),
                event.getDescription(),
                event.getEventDate(),
                new Location(
                        event.getLat(),
                        event.getLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getRequestModeration(),
                event.getTitle());
    }

    public static Event toEventUpdate(UpdateEventRequest updateEventRequest) {
        return new Event(
                updateEventRequest.getEventId(),
                updateEventRequest.getAnnotation(),
                null,
                null,
                null,
                updateEventRequest.getDescription(),
                updateEventRequest.getEventDate(),
                null,
                null,
                null,
                updateEventRequest.getPaid(),
                updateEventRequest.getParticipantLimit(),
                null,
                null,
                null,
                updateEventRequest.getTitle(),
                null);
    }

    public static UpdateEventRequest toEventUpdateDto(Event event) {
        return new UpdateEventRequest(
                event.getAnnotation(),
                event.getCategories().getId(),
                event.getDescription(),
                event.getEventDate(),
                event.getId(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getTitle());
    }

    public static Event toEventAdmin(AdminUpdateEventRequest adminUpdateEventRequest) {
        return new Event(
                null,
                adminUpdateEventRequest.getAnnotation(),
                null,
                null,
                null,
                adminUpdateEventRequest.getDescription(),
                adminUpdateEventRequest.getEventDate(),
                null,
                adminUpdateEventRequest.getLocation().getLat(),
                adminUpdateEventRequest.getLocation().getLon(),
                adminUpdateEventRequest.getPaid(),
                adminUpdateEventRequest.getParticipantLimit(),
                null,
                adminUpdateEventRequest.getRequestModeration(),
                null,
                adminUpdateEventRequest.getTitle(),
                null);
    }

    public static AdminUpdateEventRequest toEventAdminDto(Event event) {
        return new AdminUpdateEventRequest(
                event.getAnnotation(),
                null,
                event.getDescription(),
                event.getEventDate(),
                new LocationDto(
                        event.getLat(),
                        event.getLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getRequestModeration(),
                event.getTitle());
    }
}
