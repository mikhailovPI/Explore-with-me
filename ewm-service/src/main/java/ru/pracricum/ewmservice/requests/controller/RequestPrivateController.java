package ru.pracricum.ewmservice.requests.controller;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.exception.NotFoundException;
import ru.pracricum.ewmservice.requests.dto.ParticipationRequestDto;
import ru.pracricum.ewmservice.requests.service.RequestsService;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
@Slf4j
@Validated
public class RequestPrivateController {

    public static RequestsService requestsService;

    @GetMapping
    public List<ParticipationRequestDto> getInformationRequest(@PathVariable Long userId) {
        log.info("URL: /users/{userId}/requests. GetMapping/Получение информации о заявках текуущего пользователя " +
                "на участие в событии");
        return requestsService.getInformationRequest(userId);
    }

    @PostMapping
    public ParticipationRequestDto createRequest(
            @PathVariable Long userId,
            @NotNull @RequestParam(name = "eventId", required = false) Long eventId) {
        log.info("URL: /users/{userId}/requests. PostMapping/Получение всех пользователей");
        return requestsService.createRequest(userId, eventId);
    }

    @PatchMapping(path = "/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId) throws ValidationException {
        log.info("URL: /users/{userId}/requests/{requestId}/cancel. PatchMapping/Отмена запроса на участие");
        return requestsService.cancelRequest(userId, requestId);
    }
}
