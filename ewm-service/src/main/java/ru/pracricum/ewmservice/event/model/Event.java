package ru.pracricum.ewmservice.event.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.pracricum.ewmservice.categories.model.Categories;
import ru.pracricum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "events", schema = "public")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    Long id;

    @Column(name = "event_annotation")
    String annotation;

    @OneToOne
    @JoinColumn(name = "category_id")
    Categories categories;

    @Column(name = "conformed_requests")
    Integer conformedRequests;

    @Column(name = "created_on")
    LocalDateTime createdOn;

    @Column(name = "event_description")
    String description;

    @Column(name = "event_data")
    LocalDateTime eventData;

    @OneToOne
    @JoinColumn(name = "initiator_id")
    User initiator;

    @Column(name = "lap")
    Double lap;

    @Column(name = "lon")
    Double lon;

    @Column(name = "paid")
    Boolean paid;

    @Column(name = "participant_limit")
    Integer participantLimit;

    @Column(name = "request_moderation")
    LocalDateTime publishedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    EventState state;

    @Column(name = "title")
    String title;

    @Column(name = "views")
    Long views;
}