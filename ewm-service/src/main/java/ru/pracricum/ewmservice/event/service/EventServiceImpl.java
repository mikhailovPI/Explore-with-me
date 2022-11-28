package ru.pracricum.ewmservice.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.pracricum.ewmservice.categories.model.Categories;
import ru.pracricum.ewmservice.categories.repository.CategoriesRepository;
import ru.pracricum.ewmservice.event.dto.*;
import ru.pracricum.ewmservice.event.mapper.EventMapper;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.model.EventState;
import ru.pracricum.ewmservice.event.repository.EventRepository;
import ru.pracricum.ewmservice.exception.NotFoundException;
import ru.pracricum.ewmservice.requests.dto.ParticipationRequestDto;
import ru.pracricum.ewmservice.requests.mapper.RequestMapper;
import ru.pracricum.ewmservice.requests.model.ParticipationStatus;
import ru.pracricum.ewmservice.requests.model.Requests;
import ru.pracricum.ewmservice.requests.repository.RequestsRepository;
import ru.pracricum.ewmservice.user.model.User;
import ru.pracricum.ewmservice.user.repository.UserRepository;
import ru.pracricum.ewmservice.util.PageRequestOverride;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;
    private final RequestsRepository requestsRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<EventShortDto> getEvents(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            String sort,
            int from,
            int size) throws ValidationException {
        rangeStart = (rangeStart != null) ? rangeStart : LocalDateTime.now();
        rangeEnd = (rangeEnd != null) ? rangeEnd : LocalDateTime.now().plusYears(300);

        if (rangeStart.isAfter(rangeEnd)) {
            throw new ValidationException("Время окончания события не может быть раньше времени начала события");
        }
        List<Event> events;
        if (categories != null) {
            events = eventRepository.findByCategoryIdsAndText(text, categories);
        } else {
            events = eventRepository.findByText(text);
        }

        return events
                .stream()
                .map(EventMapper::toEventShortDto)
                .sorted(Comparator.comparing(EventShortDto::getEventDate))
                .skip(from)
                .limit(size)
                .collect(toList());
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        Event event = validationEvent(eventId);
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
    public List<EventShortDto> getEventsByUser(
            Long userId,
            int from,
            int size) {
        PageRequestOverride pageRequest = PageRequestOverride.of(from, size);
        User user = validationUser(userId);

        return eventRepository.findByInitiator_IdOrderByEventDateDesc(userId, pageRequest)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(toList());
    }

    @Override
    public EventFullDto getEventByIdForUser(
            Long userId,
            Long eventId) {
        User user = validationUser(userId);
        Event event = validationEvent(eventId);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsParticipationInEvent(
            Long userId,
            Long eventId) {
        User user = validationUser(userId);
        Event event = validationEvent(eventId);
        if (!userId.equals(event.getInitiator().getId())) {
            throw new NotFoundException(
                    "Данный пользователь не может обновлять текущее событие");
        }
        return requestsRepository.findByEvent(eventId)
                .stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto createEvent(
            Long userId,
            NewEventDto newEventDto) {
        validationBodyEvent(newEventDto);
        User user = validationUser(userId);
        Categories categories = validationCategories(newEventDto.getCategory());
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
    public EventFullDto updateEventByUser(
            Long userId,
            UpdateEventRequest updateEventRequest) {
        User user = validationUser(userId);
        LocalDateTime now = LocalDateTime.now();
        if (updateEventRequest.getEventDate().isBefore(now.plusHours(2))) {
            throw new NotFoundException(
                    "Время начала события не может быть раньше, чем через два часа от текущего времени");
        }
        Event event = validationEvent(updateEventRequest.getEventId());
        Categories categories = validationCategories(updateEventRequest.getCategory());
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
    public EventFullDto cancelEventForUser(
            Long userId,
            Long eventId) {
        User user = validationUser(userId);
        Event event = validationEvent(eventId);
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
    public ParticipationRequestDto confirmRequestsParticipationForEvent(
            Long userId,
            Long eventId,
            Long reqId) {
        User user = validationUser(userId);
        Event event = validationEvent(eventId);
        Requests requests = validationRequests(eventId, reqId);

        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        checkRequestsParticipation(userId, eventFullDto);

        requests.setStatus(ParticipationStatus.CONFIRMED);
        return RequestMapper.toRequestDto(requestsRepository.save(requests));
    }


    @Override
    @Transactional
    public ParticipationRequestDto rejectRequestsParticipationForEvent(
            Long userId,
            Long eventId,
            Long reqId) {
        User user = validationUser(userId);
        Event event = validationEvent(eventId);
        Requests requests = validationRequests(eventId, reqId);

        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        checkRequestsParticipation(userId, eventFullDto);

        requests.setStatus(ParticipationStatus.REJECTED);
        return RequestMapper.toRequestDto(requestsRepository.save(requests));
    }


    @Override
    public List<EventFullDto> searchEvents(
            List<Long> usersId,
            List<EventState> eventStates,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            int from,
            int size) {
        PageRequestOverride pageRequest = PageRequestOverride.of(from, size);
        LocalDateTime start = null;
        LocalDateTime end = null;
//        if (rangeStart != null) {
//            try {
//                start = LocalDateTime.parse(rangeStart, formatter);
//            } catch (NotFoundException e) {
//                throw new NotFoundException("Не корректное время начала диапазона " + rangeStart);
//            }
//        }
//        if (rangeEnd != null) {
//            try {
//                end = LocalDateTime.parse(rangeEnd, formatter);
//            } catch (NotFoundException e) {
//                throw new NotFoundException("Не корректное время окончания диапазона " + rangeEnd);
//            }
//        }

        start = (rangeStart != null) ? start : LocalDateTime.now();
        end = (rangeEnd != null) ? end : LocalDateTime.now().plusYears(300);

        if (start.isAfter(end)) {
            throw new NotFoundException(
                    "Время начала события не может быть позже, чем время окончания");
        }
        if (eventStates == null) {
            eventStates = new ArrayList<>();
            eventStates.add(EventState.PENDING);
            eventStates.add(EventState.CANCELED);
            eventStates.add(EventState.PUBLISHED);
        }
        List<Event> events = new ArrayList<>();
        if ((categories != null) && (usersId != null)) {
            events = eventRepository.findByInitiatorAndCategoriesAndState(
                    usersId,
                    categories,
                    eventStates,
                    pageRequest);
        }
        if ((categories == null) && (usersId == null)) {
            events = eventRepository.findByState(eventStates, pageRequest);
        }
        if ((categories != null) && (usersId == null)) {
            events = eventRepository.findByCategoriesAndState(categories, eventStates, pageRequest);
        }
        if ((categories == null) && (usersId != null)) {
            events = eventRepository.findByInitiatorAndState(usersId, eventStates, pageRequest);
        }
        return events
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(toList());
    }

    @Override
    @Transactional
    public EventFullDto putEvent(
            Long eventId,
            AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = validationEvent(eventId);
        Categories categories = validationCategories(adminUpdateEventRequest.getCategory());

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
        Event event = validationEvent(eventId);
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
        Event event = validationEvent(eventId);
        if (event.getState().equals(EventState.PENDING) || event.getState().equals(EventState.REJECTED)) {
            event.setState(EventState.CANCELED);
            Event eventSave = eventRepository.save(event);
            return EventMapper.toEventFullDto(eventSave);
        } else if (event.getState().equals(EventState.CANCELED)) {
            throw new NotFoundException("Событие уже отклонено");
        } else {
            throw new NotFoundException("Невозможно отклонено событие, поскольку оно уже опубликовано");
        }
    }

    public List<Event> getEventsByIds(List<Long> ids) {
        return eventRepository.findByIdIn(ids);
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
        if (annotation != null) {
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

    private User validationUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));
    }

    private Event validationEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
    }

    private Categories validationCategories(Long catId) {
        return categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория %s не существует.", catId)));
    }

    private Requests validationRequests(Long eventId, Long reqId) {
        return requestsRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Запрос на участие %s не существует.", eventId)));
    }

    private static void validationBodyEvent(NewEventDto newEventDto) {
        if (newEventDto.getAnnotation() == null) {
            throw new NotFoundException("Аннотация не может быть пустым");
        }
        if (newEventDto.getDescription() == null) {
            throw new NotFoundException("Описание не может быть пустым");
        }
    }

    private void checkRequestsParticipation(Long userId, EventFullDto eventFullDto) {
        if (!eventFullDto.getInitiator().getId().equals(userId)) {
            throw new NotFoundException("Подтверждение участие может делать только создатель события");
        }
        if (!eventFullDto.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("Невозможно участвовать в неопубликованном мероприятии");
        }
        if (eventFullDto.getConfirmedRequests() == eventFullDto.getParticipantLimit()) {
            throw new NotFoundException("Сводобных мест на событие нет");
        }
    }
}
