package ru.pracricum.ewmservice.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pracricum.ewmservice.comments.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

}
