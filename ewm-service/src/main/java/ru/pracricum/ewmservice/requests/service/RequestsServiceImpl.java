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
import ru.pracricum.ewmservice.user.repository.UserRepository;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestsServiceImpl implements RequestsService {

    private final RequestsRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getInformationRequest(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));
        List<ParticipationRequestDto> requestDtoList = requestRepository.findByRequester(userId)
                .stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        return requestDtoList;
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));
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
        ParticipationRequestDto requestDto = new ParticipationRequestDto(
                null,
                LocalDateTime.now(),
                eventId,
                userId,
                ParticipationStatus.PENDING);

        try {
            return RequestMapper.toRequestDto(requestRepository.save(RequestMapper.toRequest(requestDto)));
        } catch (RuntimeException e) {
            throw new NotFoundException("Заяка научастие уже подана");
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) throws ValidationException {
        Requests request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request for participation in the event with id = " + requestId +
                        " not found"));

        if (!userId.equals(request.getRequester())) {
            throw new ValidationException("Only the user who created the request can cancel it");
        }
        request.setStatus(ParticipationStatus.CANCELED);
        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
        return dto;
    }
}
