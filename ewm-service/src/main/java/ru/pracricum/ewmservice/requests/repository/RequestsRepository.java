package ru.pracricum.ewmservice.requests.repository;


import org.apache.catalina.connector.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pracricum.ewmservice.requests.model.Requests;

import java.util.List;

public interface RequestsRepository extends JpaRepository<Requests, Long> {

    List<Requests> findByRequester(Long userId);

    List<Requests> findByEvent(Long eventId);
}
