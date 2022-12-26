package ru.pracricum.ewmservice.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pracricum.ewmservice.comments.model.Comments;
import ru.pracricum.ewmservice.util.PageRequestOverride;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> findCommentOrderByEventId(Long eventId, PageRequestOverride pageRequest);
    void deleteCommentOrderByEventId(Long eventId);
    List<Comments> findCommentOrderByUserId(Long userId, PageRequestOverride pageRequest);

}
