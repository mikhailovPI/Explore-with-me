package ru.pracricum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.model.EventState;
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
    List<Event> findByCategoryIdsAndText(
            String text,
            List<Long> categories);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.categories.id IN :categories) AND " +
            "(e.initiator.id IN :usersId) AND " +
            "(e.state IN :eventStates)")
    List<Event> findByInitiatorAndCategoriesAndState(
            List<Long> usersId,
            List<Long> categories,
            List<EventState> eventStates,
            PageRequestOverride pageRequest);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.state IN :eventStates)")
    List<Event> findByState(
            List<EventState> eventStates,
            PageRequestOverride pageRequest);


    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.categories.id IN :categories) AND " +
            "(e.state IN :eventStates)")
    List<Event> findByCategoriesAndState(
            List<Long> categories,
            List<EventState> eventStates,
            PageRequestOverride pageRequest);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.initiator.id IN :usersId) AND " +
            "(e.state IN :eventStates)")
    List<Event> findByInitiatorAndState(
            List<Long> usersId,
            List<EventState> eventStates,
            PageRequestOverride pageRequest);
}
