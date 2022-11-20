package ru.pracricum.ewmservice.event.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pracricum.ewmservice.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
