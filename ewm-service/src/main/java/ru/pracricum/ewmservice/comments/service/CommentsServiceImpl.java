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
import ru.pracricum.ewmservice.event.repository.EventRepository;
import ru.pracricum.ewmservice.exception.NotFoundException;
import ru.pracricum.ewmservice.user.model.User;
import ru.pracricum.ewmservice.user.repository.UserRepository;
import ru.pracricum.ewmservice.util.PageRequestOverride;

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
        Event event = validationEvent(eventId);

        //commentsRepository.
        return event.getCommentsList()
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
        return null;
    }

    @Override
    @Transactional
    public CommentsDto createdComment(Long eventId, CommentsDto commentsDto) {
        validationBodyComment(commentsDto);
        validationEvent(eventId);
        User user = validationUser(commentsDto.getUser());
        Comments comments = CommentsMapper.toComment(commentsDto);
        comments.setUser(user);
        commentsRepository.save(comments);
        return CommentsMapper.toCommentDto(commentsRepository.save(comments));
    }

    @Override
    @Transactional
    public CommentsDto patchComment(Long eventId, Long commentId, CommentsDto commentsDto) {
        validationEvent(eventId);
        validationBodyComment(commentsDto);
        Comments comments = validationComments(commentId);
        comments.setText(commentsDto.getText());
        Comments comments1 = commentsRepository.save(comments);

        return CommentsMapper.toCommentDto(comments1);
    }

    @Override
    @Transactional
    public void deleteComments(Long eventId) {
        //Event event = validationEvent(eventId);
        //event.getCommentsList().removeAll(event.getCommentsList());
        //int bound = event.getCommentsList().size();
        //for (int i = 0; i < bound; i++) {
        //}
    }

    @Override
    @Transactional
    public void deleteCommentById(Long eventId, Long commentId) {
        validationEvent(eventId);
        commentsRepository.deleteById(commentId);
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