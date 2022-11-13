package ru.pracricum.ewmservice.event.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.pracricum.ewmservice.category.model.Category;
import ru.pracricum.ewmservice.location.model.Location;
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

    @Column(name = "event_description")
    String description;

    @OneToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Column(name = "conformed_requests")
    Integer conformedRequests;

    @Column(name = "participant_limit")
    Integer participantLimit;

    @OneToOne
    @JoinColumn(name = "initiator_id")
    User initiator;

    @OneToOne
    @JoinColumn(name = "location_id")
    Location location;

    @Column(name = "created_on")
    LocalDateTime createdOn;

    @Column(name = "event_data")
    LocalDateTime eventData;

    @Column(name = "request_moderation")
    LocalDateTime requestModeration;

    @OneToOne
    @JoinColumn(name = "price_id")
    Price price;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    EventState state;

    @Column(name = "title")
    String title;

    @Column(name = "views")
    Long views;
}