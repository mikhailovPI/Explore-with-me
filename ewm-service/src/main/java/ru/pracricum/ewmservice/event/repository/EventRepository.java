package ru.pracricum.ewmservice.event.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.user.model.User;
import ru.pracricum.ewmservice.util.PageRequestOverride;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiator_IdOrderByEventDateDesc(Long id, PageRequestOverride page);

    List<Event> findByIdIn(List<Long> ids);
}
