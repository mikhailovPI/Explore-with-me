package ru.pracricum.ewmservice.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import ru.pracricum.ewmservice.categories.model.Categories;
import ru.pracricum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.pracricum.ewmservice.categories.model.Categories.CATEGORIES_ID;
import static ru.pracricum.ewmservice.event.model.Event.SCHEMA_TABLE;
import static ru.pracricum.ewmservice.event.model.Event.TABLE_EVENTS;
import static ru.pracricum.ewmservice.user.model.User.USERS_ID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = TABLE_EVENTS, schema = SCHEMA_TABLE)
@Validated
public class Event {

    public static final String TABLE_EVENTS = "events";
    public static final String SCHEMA_TABLE = "public";
    public static final String EVENTS_ID = "event_id";
    public static final String EVENTS_ANNOTATION = "event_annotation";
    public static final String EVENTS_CONFORMED_REQUESTS = "conformed_requests";
    public static final String EVENTS_CREATED_ON = "created_on";
    public static final String EVENTS_DESCRIPTION = "event_description";
    public static final String EVENTS_DATE = "event_date";
    public static final String EVENTS_LAT = "lat";
    public static final String EVENTS_LON = "lon";
    public static final String EVENTS_PAID = "paid";
    public static final String EVENTS_PARTICIPANT_LIMIT = "participant_limit";
    public static final String EVENTS_PUBLISHED_ON = "published_on";
    public static final String EVENTS_REQUEST_MODERATION = "request_moderation";
    public static final String EVENTS_STATE = "state";
    public static final String EVENTS_COMMENTS = "comment_id";
    public static final String EVENTS_TITLE = "title";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EVENTS_ID)
    Long id;

    @Column(name = EVENTS_ANNOTATION)
    String annotation;

    @OneToOne
    @JoinColumn(name = CATEGORIES_ID)
    Categories categories;

    @Column(name = EVENTS_CONFORMED_REQUESTS)
    Integer conformedRequests;

    @Column(name = EVENTS_CREATED_ON)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    @Column(name = EVENTS_DESCRIPTION)
    String description;

    @Column(name = EVENTS_DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @OneToOne
    @JoinColumn(name = USERS_ID)
    User initiator;

    @Column(name = EVENTS_LAT)
    Double lat;

    @Column(name = EVENTS_LON)
    Double lon;

    @Column(name = EVENTS_PAID)
    Boolean paid;

    @Column(name = EVENTS_PARTICIPANT_LIMIT)
    Integer participantLimit;

    @Column(name = EVENTS_PUBLISHED_ON)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;

    @Column(name = EVENTS_REQUEST_MODERATION)
    Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = EVENTS_STATE)
    EventState state;

    @Column(name = EVENTS_TITLE)
    String title;
}