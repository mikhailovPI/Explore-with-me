package ru.pracricum.ewmservice.requests.service;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;
import ru.pracricum.ewmservice.requests.dto.ParticipationRequestDto;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public interface RequestsService {
    List<ParticipationRequestDto> getInformationRequest(Long userId);

    ParticipationRequestDto createRequest(Long userId, @NotNull Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId) throws ValidationException;
}
