package ru.pracricum.ewmservice.event.mapper;

import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.model.Categories;
import ru.pracricum.ewmservice.event.dto.*;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.model.Location;
import ru.pracricum.ewmservice.user.dto.UserShortDto;
import ru.pracricum.ewmservice.user.model.User;

public class EventMapper {

    public static Event toEvent(EventFullDto eventFullDto) {
        return new Event(
                eventFullDto.getId(),
                eventFullDto.getAnnotation(),
                new Categories(
                        eventFullDto.getCategory().getId(),
                        eventFullDto.getCategory().getName()),
                eventFullDto.getConformedRequests(),
                eventFullDto.getCreatedOn(),
                eventFullDto.getDescription(),
                eventFullDto.getEventData(),
                new User(
                        eventFullDto.getInitiator().getId(),
                        eventFullDto.getInitiator().getName(),
                        null),
                eventFullDto.getLocation().getLap(),
                eventFullDto.getLocation().getLon(),
                eventFullDto.getPaid(),
                eventFullDto.getParticipantLimit(),
                eventFullDto.getPublishedOn(),
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
                event.getEventData(),
                new UserShortDto(
                        event.getInitiator().getId(),
                        event.getInitiator().getName()),
                new Location(
                        event.getLap(),
                        event.getLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
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
                eventShortDto.getConformedRequests(),
                null,
                null,
                eventShortDto.getEventData(),
                null,
                null,
                null,
                eventShortDto.getPaid(),
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
                event.getEventData(),
                new UserShortDto(
                        event.getInitiator().getId(),
                        event.getInitiator().getName()),
                event.getPaid(),
                event.getTitle(),
                event.getViews());
    }

    public static Event toEventNew(NewEventDto newEventDto) {
        return new Event(
                null, // нет id
                newEventDto.getAnnotation(),
                null,
                null,
                null,
                newEventDto.getDescription(),
                newEventDto.getEventData(),
                null,
                newEventDto.getLocation().getLap(),
                newEventDto.getLocation().getLon(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(),
                null,
                newEventDto.getTitle(),
                null);
    }

    public static NewEventDto toEventNewDto(Event event) {
        return new NewEventDto(
                event.getAnnotation(),
                event.getCategories().getId(),
                event.getDescription(),
                event.getEventData(),
                new Location(
                        event.getLap(),
                        event.getLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getTitle());
    }

    public static Event toEventUpdate(UpdateEventRequest updateEventRequest) {
        return new Event(
                null,
                updateEventRequest.getAnnotation(),
                null,
                null,
                null,
                updateEventRequest.getDescription(),
                updateEventRequest.getEventData(),
                null,
                null,
                null,
                updateEventRequest.getPaid(),
                updateEventRequest.getParticipantLimit(),
                null,
                null,
                updateEventRequest.getTitle(),
                null);
    }

    public static UpdateEventRequest toEventUpdateDto (Event event) {
        return new UpdateEventRequest(
                event.getAnnotation(),
                event.getCategories().getId(),
                event.getDescription(),
                event.getEventData(),
                event.getId(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getTitle());
    }

    public static Event toEventAdmin (AdminUpdateEventRequest adminUpdateEventRequest) {
        return new Event(
                null,
                adminUpdateEventRequest.getAnnotation(),
                new Categories(
                        adminUpdateEventRequest.getCategory().getId(),
                        adminUpdateEventRequest.getCategory().getName()),
                null,
                null,
                adminUpdateEventRequest.getDescription(),
                adminUpdateEventRequest.getEventData(),
                null,
                adminUpdateEventRequest.getLocation().getLap(),
                adminUpdateEventRequest.getLocation().getLon(),
                adminUpdateEventRequest.getPaid(),
                adminUpdateEventRequest.getParticipantLimit(),
                adminUpdateEventRequest.getRequestModeration(),
                null,
                adminUpdateEventRequest.getTitle(),
                null);
    }

    public static AdminUpdateEventRequest toEventAdminDto (Event event) {
        return new  AdminUpdateEventRequest(
                event.getAnnotation(),
                new CategoriesDto(
                        event.getCategories().getId(),
                        event.getCategories().getName()),
                event.getDescription(),
                event.getEventData(),
                new Location(
                        event.getLap(),
                        event.getLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getTitle());
    }

    public static Event toEventParticipation (ParticipationRequestDto participationRequestDto) {
        return new Event(

        );
    }

}
