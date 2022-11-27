package ru.pracricum.ewmservice.event.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.util.PageRequestOverride;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiator_IdOrderByEventDateDesc(Long id, PageRequestOverride page);

    List<Event> findByIdIn(List<Long> ids);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (LOWER(e.annotation) LIKE CONCAT('%',LOWER(:text),'%') OR " +
            "LOWER(e.description) LIKE CONCAT('%',LOWER(:text),'%'))")
    List<Event> findByText(String text);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.categories.id IN :categories) AND " +
            "(LOWER(e.annotation) LIKE CONCAT('%',LOWER(:text),'%') OR " +
            "LOWER(e.description) LIKE CONCAT('%',LOWER(:text),'%'))")
    List<Event> findByCategoryIdsAndText(String text, List<Long> categories);
}
