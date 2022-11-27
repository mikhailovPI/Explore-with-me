package ru.pracricum.ewmservice.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestsServiceImpl implements RequestsService {

    private final RequestsRepository requestsRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getInformationRequest(Long userId) {
        User user = validationUser(userId);
        return requestsRepository.findByRequester(userId)
                .stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event event = validationEvent(eventId);
        validationUser(userId);
        if (!event.getRequestModeration()) {
            if (event.getInitiator().getId().equals(userId)) {
                throw new NotFoundException("Организатор не может быть участником события");
            }
            if (!event.getState().equals(EventState.PUBLISHED)) {
                throw new NotFoundException("Событие еще не опубликовано");
            }
            if ((event.getParticipantLimit() - event.getConformedRequests()) <= 0) {
                throw new NotFoundException("Свободных мест нет!");
            }
        }
        LocalDateTime time = LocalDateTime.now();
        ParticipationRequestDto requestDto = new ParticipationRequestDto();
        requestDto.setCreated(time);
        requestDto.setEvent(eventId);
        requestDto.setRequester(userId);
        requestDto.setStatus(ParticipationStatus.PENDING);

        Requests request = RequestMapper.toRequest(requestDto);
        Requests requestsSave = requestsRepository.save(request);

        return RequestMapper.toRequestDto(requestsSave);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) throws ValidationException {
        Requests requests = validationRequests(requestId);

        if (!userId.equals(requests.getRequester())) {
            throw new ValidationException("Отменить запрос может пользователь, создавший его");
        }
        requests.setStatus(ParticipationStatus.CANCELED);
        return RequestMapper.toRequestDto(requestsRepository.save(requests));
    }

    private Requests validationRequests(Long requestId) {
        return requestsRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Запрос на участие %s не существует.", requestId)));
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
}
