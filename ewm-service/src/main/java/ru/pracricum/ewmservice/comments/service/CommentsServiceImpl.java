package ru.pracricum.ewmservice.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pracricum.ewmservice.comments.dto.CommentsDto;
import ru.pracricum.ewmservice.comments.mapper.CommentsMapper;
import ru.pracricum.ewmservice.comments.model.Comments;
import ru.pracricum.ewmservice.comments.repository.CommentsRepository;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.model.EventState;
import ru.pracricum.ewmservice.event.repository.EventRepository;
import ru.pracricum.ewmservice.exception.NotFoundException;
import ru.pracricum.ewmservice.user.model.User;
import ru.pracricum.ewmservice.user.repository.UserRepository;
import ru.pracricum.ewmservice.util.PageRequestOverride;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentsDto> getComments(Long eventId, int from, int size) {
        PageRequestOverride pageRequest = PageRequestOverride.of(from, size);
        validationEvent(eventId);

        return commentsRepository.findCommentOrderByEventId(eventId, pageRequest)
                .stream()
                .map(CommentsMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentsDto getCommentById(Long eventId, Long commentId) {
        validationEvent(eventId);
        Comments comments = commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Комментарий %s не существует.", commentId)));
        return CommentsMapper.toCommentDto(comments);
    }

    @Override
    public List<CommentsDto> getCommentsByUser(Long userId, int from, int size) {
        PageRequestOverride pageRequest = PageRequestOverride.of(from, size);
        validationUser(userId);
        return commentsRepository.findCommentOrderByUserId(userId, pageRequest)
                .stream()
                .map(CommentsMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentsDto createdComment(Long eventId, CommentsDto commentsDto) {
        LocalDateTime created = LocalDateTime.now();
        validationBodyComment(commentsDto);
        Event event = validationEvent(eventId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            User user = validationUser(commentsDto.getUser());
            Comments comments = CommentsMapper.toComment(commentsDto);
            comments.setUser(user);
            comments.setEvent(event);
            comments.setPublishedOn(created);
            commentsRepository.save(comments);
            return CommentsMapper.toCommentDto(commentsRepository.save(comments));
        } else {
            throw new NotFoundException(
                    String.format("Событие %s опубликовано. Оставить комментарий невозможно.", eventId));
        }
    }

    @Override
    @Transactional
    public CommentsDto patchComment(Long eventId, Long commentId, Long userId, CommentsDto commentsDto) {
        if (commentsDto.getUser().equals(userId)) {
            validationEvent(eventId);
            validationBodyComment(commentsDto);
            Comments comments = validationComments(commentId);
            comments.setText(commentsDto.getText());
            Comments comments1 = commentsRepository.save(comments);
            return CommentsMapper.toCommentDto(comments1);
        } else {
            throw new NotFoundException(
                    String.format("Пользователь %s не может обновить чужой комментарий.", userId));        }
    }

    @Override
    @Transactional
    public void deleteComments(Long eventId) {
        commentsRepository.deleteCommentOrderByEventId(eventId);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long eventId, Long commentId, Long userId) {
        Comments comments = validationComments(commentId);
        if (comments.getUser().getId().equals(userId)) {
            validationEvent(eventId);
            commentsRepository.deleteById(commentId);
        } else {
            throw new NotFoundException(
                    String.format("Пользователь %s не может удалить чужой комментарий.", userId));
        }
    }

    private Event validationEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
    }

    private Comments validationComments(Long commentId) {
        return commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Комментарий %s не существует.", commentId)));
    }

    private User validationUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));
    }

    private static void validationBodyComment(CommentsDto commentsDto) {
        if (commentsDto.getText().isEmpty()) {
            throw new NotFoundException("Текст комментария не может быть пустым");
        }
    }
}
