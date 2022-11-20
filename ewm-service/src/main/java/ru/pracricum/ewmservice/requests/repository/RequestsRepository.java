package ru.pracricum.ewmservice.requests.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pracricum.ewmservice.requests.model.Requests;

public interface RequestsRepository extends JpaRepository<Requests, Long> {
}
