package ru.pracricum.ewmservice.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.model.Categories;
import ru.pracricum.ewmservice.categories.repository.CategoriesRepository;
import ru.pracricum.ewmservice.event.dto.*;
import ru.pracricum.ewmservice.event.mapper.EventMapper;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.model.EventState;
import ru.pracricum.ewmservice.event.repository.EventRepository;
import ru.pracricum.ewmservice.exception.NotFoundException;
import ru.pracricum.ewmservice.requests.dto.ParticipationRequestDto;
import ru.pracricum.ewmservice.user.dto.UserDto;
import ru.pracricum.ewmservice.user.model.User;
import ru.pracricum.ewmservice.user.repository.UserRepository;
import ru.pracricum.ewmservice.util.PageRequestOverride;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;

    @Override
    public List<EventShortDto> getEvents(
            String text,
            List<CategoriesDto> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            String sort,
            int from,
            int size) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
        if (event.getState().equals(EventState.PUBLISHED)) {
            Long views = event.getViews();
            event.setViews(views + 1);
            Event eventSave = eventRepository.save(event);
            return EventMapper.toEventFullDto(eventSave);
        } else {
            throw new NotFoundException("Событие нельзя посмотреть, т.к. оно не опубликовано");
        }
    }

    @Override
    public List<EventShortDto> getEventsByUser(Long userId, int from, int size) {
        PageRequestOverride pageRequest = PageRequestOverride.of(from, size);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));

        return eventRepository.findByInitiator_IdOrderByEventDateDesc(userId, pageRequest)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventByIdForUser(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));

        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsParticipationInEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));
        Categories categories = categoriesRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория %s не существует.", newEventDto.getCategory())));

        LocalDateTime now = LocalDateTime.now();

        if (newEventDto.getEventDate().isBefore(now.plusHours(2))) {
            throw new NotFoundException(
                    "Время начала события не может быть раньше, чем через два часа от текущего времени");
        }
        Event event = EventMapper.toEventNew(newEventDto);

        event.setCreatedOn(LocalDateTime.now());
        event.setConformedRequests(0);
        event.setInitiator(user);
        event.setCategories(categories);
        event.setViews(0L);

        Event eventSave = eventRepository.save(event);

        return EventMapper.toEventFullDto(eventSave);
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUser(Long userId, UpdateEventRequest updateEventRequest) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));

        LocalDateTime now = LocalDateTime.now();

        if (updateEventRequest.getEventDate().isBefore(now.plusHours(2))) {
            throw new NotFoundException(
                    "Время начала события не может быть раньше, чем через два часа от текущего времени");
        }

        Event event = eventRepository.findById(updateEventRequest.getEventId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", updateEventRequest.getEventId())));
        Categories categories = categoriesRepository.findById(updateEventRequest.getCategory())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория %s не существует.", updateEventRequest.getCategory())));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new NotFoundException(
                    "Данный пользователь не может обновлять текущее событие");
        }

        updateEvent(
                event,
                categories,
                updateEventRequest.getAnnotation(),
                updateEventRequest.getCategory(),
                updateEventRequest.getDescription(),
                updateEventRequest.getEventDate(),
                updateEventRequest.getPaid(),
                updateEventRequest.getParticipantLimit(),
                updateEventRequest.getTitle());

        Event eventSave = eventRepository.save(event);
        return EventMapper.toEventFullDto(eventSave);
    }

    @Override
    @Transactional
    public EventFullDto cancelEventForUser(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
        if (!userId.equals(event.getInitiator().getId())) {
            throw new NotFoundException(
                    "Данный пользователь не может обновлять текущее событие");
        }

        if (event.getState().equals(EventState.PENDING)) {
            event.setState(EventState.CANCELED);
            Event eventSave = eventRepository.save(event);
            return EventMapper.toEventFullDto(eventSave);
        } else {
            throw new NotFoundException(
                    "Отменить событие можно только в статусе ожидания публикации.");
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequestsParticipationForEvent(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequestsParticipationForEvent(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    public List<EventFullDto> searchEvents(
            List<UserDto> usersId,
            List<EventState> eventStates,
            List<CategoriesDto> categories,
            LocalDateTime startSearch,
            LocalDateTime endSearch,
            int from,
            int size) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto putEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
        Categories categories = categoriesRepository.findById(adminUpdateEventRequest.getCategory())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория %s не существует.", adminUpdateEventRequest.getCategory())));

        LocalDateTime now = LocalDateTime.now();
        if (adminUpdateEventRequest.getEventDate().isBefore(now.plusHours(2))) {
            throw new NotFoundException(
                    "Время начала события не может быть раньше, чем через два часа от текущего времени");
        }

        updateEvent(
                event,
                categories,
                adminUpdateEventRequest.getAnnotation(),
                adminUpdateEventRequest.getCategory(),
                adminUpdateEventRequest.getDescription(),
                adminUpdateEventRequest.getEventDate(),
                adminUpdateEventRequest.getPaid(),
                adminUpdateEventRequest.getParticipantLimit(),
                adminUpdateEventRequest.getTitle());

        if (adminUpdateEventRequest.getLocation() != null) {
            event.setLon(adminUpdateEventRequest.getLocation().getLon());
            event.setLat(adminUpdateEventRequest.getLocation().getLat());
        }
        if (adminUpdateEventRequest.getRequestModeration() != null) {
            event.getRequestModeration();
        }
        Event eventSave = eventRepository.save(event);
        return EventMapper.toEventFullDto(eventSave);
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
        if (event.getState().equals(EventState.PENDING)) {
            event.setState(EventState.PUBLISHED);
            event.setRequestModeration(true);
            Event eventSave = eventRepository.save(event);
            return EventMapper.toEventFullDto(eventSave);
        } else if (event.getState().equals(EventState.CANCELED)) {
            throw new NotFoundException(
                    "Событие не может быть опубликовано, посколько оно отменено");
        } else {
            throw new NotFoundException(
                    "Событие уже опубликовано");
        }
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
        if (event.getState().equals(EventState.PENDING) || event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.REJECTED);
            Event eventSave = eventRepository.save(event);
            return EventMapper.toEventFullDto(eventSave);
        } else if (event.getState().equals(EventState.REJECTED)) {
            throw new NotFoundException("Событие уже отклонено");
        } else {
            throw new NotFoundException("Невозможно отклонено событие, поскольку оно уже опубликовано");
        }
    }

    private void updateEvent(Event event,
                             Categories categories,
                             String annotation,
                             Long category,
                             String description,
                             LocalDateTime eventDate,
                             Boolean paid,
                             Integer participantLimit,
                             String title) {
        if (annotation !=null) {
            event.setAnnotation(annotation);
        }
        if (category != null) {
            event.setCategories(categories);
        }
        if (description != null) {
            event.setDescription(description);
        }
        if (eventDate != null) {
            event.setEventDate(eventDate);
        }
        if (paid != null) {
            event.setPaid(paid);
        }
        if (participantLimit != null) {
            event.setParticipantLimit(participantLimit);
        }
        if (title != null) {
            event.setTitle(title);
        }
    }
}
